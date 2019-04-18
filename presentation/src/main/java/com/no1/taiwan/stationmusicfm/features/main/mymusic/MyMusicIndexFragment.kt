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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.playlist.CreatePlaylistEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewmodels.MyMusicIndexViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_CREATE_NEW_PLAYLIST
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_PLAYLIST_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchHidingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peelSkipLoading
import com.no1.taiwan.stationmusicfm.widget.components.dialog.InputDialog
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.toast.toastX
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MyMusicIndexFragment : IndexFragment<MyMusicIndexViewModel>() {
    private val adapter: MusicAdapter by instance(ADAPTER_PLAYLIST)
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val actionBarBlankDecoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_ACTION_BAR_BLANK)
    private val footerCreatePlaylist by lazy {
        CreatePlaylistEntity(content = getString(R.string.fragment_playlist_create_list))
    }

    init {
        BusFragLifeRegister(this)
        SearchHidingLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.playlist) {
            peelSkipLoading {
                logd(it)
            } happenError {
                loge(it)
            } doWith this@MyMusicIndexFragment
        }
        observeNonNull(vm.playlists) {
            peelSkipLoading {
                adapter.replaceWholeList(cast(it))
                adapter.setFooter(footerCreatePlaylist)
            } happenError {
                loge(it)
            } doWith this@MyMusicIndexFragment
        }
        //region 🔖 The reaction of creating a new playlist.
        // STEP 1. For creating a new playlist.
        observeNonNull(vm.addPlaylistRes) {
            peelSkipLoading {
                if (!it) {
                    toastX("There's had the same playlist name you created!")
                    return@peelSkipLoading
                }
                vm.runTaskFetchTheNewestPlaylist()
            } happenError {
                loge(it)
            } doWith this@MyMusicIndexFragment
        }
        // STEP 2. Refreshing the recyclerview for displaying the new playlist.
        observeNonNull(vm.newestPlaylist) { adapter.append(this) }
        //endregion

        // FIXME(jieyi): 2019-03-30 Maybe not be here.
        observeNonNull(vm.downloads) {
        }
        observeNonNull(vm.favorites) {
            // TODO(jieyi): 2019-03-30 Combine four image into an image.
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.runTaskFetchPlaylist()
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_playlist), adapter, linearLayoutManager(), listOf(actionBarBlankDecoration))
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = getString(R.string.fragment_title_my_music)

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_mymusic_index

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.CreatePlaylistViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_CREATE_NEW_PLAYLIST)])
    fun createNewPlaylist(playlistName: String) {
        InputDialog.createDialog(this) { v, dialogFragment ->
            v.find<TextView>(R.id.ftv_dialog_subtitle)
            v.find<Button>(R.id.btn_cancel).setOnClickListener { dialogFragment.dismiss() }
            v.find<Button>(R.id.btn_ok).setOnClickListener {
                val name = v.find<EditText>(R.id.et_playlist_name).text.toString()
                vm.runTaskAddPlaylist(name)
                dialogFragment.dismiss()
            }
        }?.show()
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.PlaylistViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_TO_PLAYLIST_DETAIL)])
    fun gotoPlaylistDetail(playlistInfo: PlaylistInfoEntity) {
        findNavController().navigate(R.id.action_frag_music_index_to_frag_music_detail,
                                     MyMusicDetailFragment.createBundle(playlistInfo))
    }
}
