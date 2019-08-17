/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.no1.taiwan.stationmusicfm.features.main.mymusic

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.gone
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.visible
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewmodels.MyMusicDetailViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK_OF_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_MUSIC_OF_PLAYLIST_SEPARATOR
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.BottomSheetFactory
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.TrackOfPlaylistNotifiableAdapter
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_REMOVING_LOCAL_MUSIC_FROM_PLAYLIST
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.file.FilePathFactory
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.toast.toastX
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MyMusicDetailFragment : AdvFragment<MainActivity, MyMusicDetailViewModel>() {
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARGUMENT_PLAYLIST_INFO = "fragment argument playlist information entity"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new bundle of this fragment.
         */
        fun createBundle(playlistInfo: PlaylistInfoEntity) =
            bundleOf(ARGUMENT_PLAYLIST_INFO to playlistInfo)
    }

    private var hasDelete = false
    private lateinit var tempSongEntity: LocalMusicEntity
    //region Arguments
    private val playlistInfo by extraNotNull<PlaylistInfoEntity>(ARGUMENT_PLAYLIST_INFO)
    //endregion
    private val adapter: TrackOfPlaylistNotifiableAdapter by instance(ADAPTER_TRACK_OF_PLAYLIST)
    private val decoration: RecyclerView.ItemDecoration by instance(
        ITEM_DECORATION_MUSIC_OF_PLAYLIST_SEPARATOR)
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val bottomSheet by lazy {
        BottomSheetFactory.createMusicSheet(parent,
                                            viewLifecycleOwner)
    }

    init {
        BusFragLifeRegister(this)
        SearchShowingLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.playlist) {
            peel {
                adapter.replaceWholeList(cast(it))
                if (hasDelete)
                    onDeletedMusicFromPlaylist(it.size)
            } happenError {
                loge(it)
            } doWith this@MyMusicDetailFragment
        }
        // After removing music from the playlist.
        observeNonNull(vm.removeRes) {
            if (this) {
                hasDelete = true
                // If remove the data from database is success then refresh the playlist.
                vm.executeRemoveLiveDataMusic(tempSongEntity.copy())
            }
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.runTaskFetchPlaylist(playlistInfo.id)
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_music), adapter, linearLayoutManager(), decoration)
        find<TextView>(R.id.ftv_playlist_name).text = playlistInfo.name
        find<TextView>(R.id.ftv_track_count).text =
            getString(R.string.viewholder_playlist_song).format(playlistInfo.trackCount)
        find<ImageView>(R.id.iv_playlist_thumbnail).loadByAny(R.drawable.thumbnail_default_playlist)
        bottomSheet.also {
            it.find<View>(R.id.ftv_download).gone()
            it.find<View>(R.id.ftv_to_playlist).gone()
            it.find<View>(R.id.ftv_music_delete).visible()
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        super.componentListenersBinding()
        bottomSheet.onDeleted = {
            vm.runTaskUpdateToPlayHistory(tempSongEntity, playlistInfo.id, false)
        }
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = getString(R.string.fragment_title_playlist)

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_mymusic_playlist_detail

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.LocalMusicViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_REMOVING_LOCAL_MUSIC_FROM_PLAYLIST)])
    fun openBottomSheetOptions(entity: LocalMusicEntity) {
        tempSongEntity = entity
        bottomSheet.show()
    }

    private fun onDeletedMusicFromPlaylist(count: Int) {
        // Update the recycler view.
        adapter.updateViewHolderItems()
        // Update number of the songs displaying.
        find<TextView>(R.id.ftv_track_count).text =
            getString(R.string.viewholder_playlist_song).format(count)
        // Only the music was downloaded, it should be deleted.
        if (playlistInfo.id == PlaylistIndex.DOWNLOADED.ordinal)
            FilePathFactory.removeMusic(FilePathFactory.obtainMusicPath(tempSongEntity.encodeByName()))
        hasDelete = false
        toastX("Success")
    }
}
