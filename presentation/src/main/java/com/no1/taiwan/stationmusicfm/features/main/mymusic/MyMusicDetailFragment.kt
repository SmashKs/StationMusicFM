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
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.gone
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewmodels.MyMusicDetailViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK_OF_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.BottomSheetFactory
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapter.TrackOfPlaylistNotifiableAdapter
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_REMOVING_LOCAL_MUSIC_FROM_PLAYLIST
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.toast.toastX
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MyMusicDetailFragment : IndexFragment<MyMusicDetailViewModel>() {
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARGUMENT_PLAYLIST_INFO = "fragment argument playlist information entity"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new bundle of this fragment.
         */
        fun createBundle(playlistInfo: PlaylistInfoEntity) = bundleOf(ARGUMENT_PLAYLIST_INFO to playlistInfo)
    }

    // The fragment initialization parameters.
    private val playlistInfo by extraNotNull<PlaylistInfoEntity>(ARGUMENT_PLAYLIST_INFO)
    private val adapter: TrackOfPlaylistNotifiableAdapter by instance(ADAPTER_TRACK_OF_PLAYLIST)
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val bottomSheet by lazy { BottomSheetFactory.createMusicSheet(parent) }
    private var hasDelete = false
    private lateinit var tempSongEntity: LocalMusicEntity

    init {
        BusFragLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.playlist) {
            peel {
                adapter.replaceWholeList(cast(it))
                if (hasDelete) {
                    adapter.updateViewHolderItems()
                    toastX("Success")
                    hasDelete = false
                }
            } happenError {
                loge(it)
            } doWith this@MyMusicDetailFragment
        }
        // After removing a music from the playlist.
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
        initRecyclerViewWith(find(R.id.rv_music), adapter, linearLayoutManager())
        find<TextView>(R.id.ftv_playlist_name).text = playlistInfo.name
        find<TextView>(R.id.ftv_track_count).text =
            getString(R.string.viewholder_playlist_song).format(playlistInfo.trackCount)
        bottomSheet.also {
            it.find<View>(R.id.ftv_download).gone()
            it.find<View>(R.id.ftv_to_playlist).gone()
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        bottomSheet.also {
            it.find<View>(R.id.ftv_music_delete).setOnClickListener {
                vm.runTaskUpdateToPlayHistory(tempSongEntity, playlistInfo.id, false)
                bottomSheet.dismiss()
            }
        }
    }

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
}
