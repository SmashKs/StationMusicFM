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

package com.no1.taiwan.stationmusicfm.features.main.rank

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.getDimen
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import com.devrapid.kotlinshaver.bkg
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.rank.viewmodels.RankDetailViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.BottomSheetFactory
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapter.NotifiableAdapter
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.FragmentArguments.COMMON_TITLE
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SONG_ENTITY
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_OPEN_BOTTOM_SHEET
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_PLAY_A_SONG
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLongerLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchHidingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.entity.transformLocalUri
import com.no1.taiwan.stationmusicfm.utils.file.FilePathFactory
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class RankDetailFragment : AdvFragment<MainActivity, RankDetailViewModel>() {
    companion object {
        private const val ARGUMENT_RANK_ID = "fragment argument rank id"

        fun createBundle(rankId: Int, title: String) = bundleOf(ARGUMENT_RANK_ID to rankId,
                                                                COMMON_TITLE to title)
    }

    private lateinit var tempSongEntity: SongEntity
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val songAdapter: NotifiableAdapter by instance(ADAPTER_TRACK)
    private val actionBarBlankDecoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_ACTION_BAR_BLANK)
    private val rankId by extraNotNull<Int>(ARGUMENT_RANK_ID)
    private val player: MusicPlayer by instance()
    private val bottomSheet by lazy { BottomSheetFactory.createMusicSheet(parent) }

    init {
        BusFragLongerLifeRegister(this)
        SearchHidingLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.rankMusic) {
            peel {
                // Add the song list into the adapter.
                songAdapter.append(cast<MusicVisitables>(it.songs))
                // Add the song list into the music player's playlist.
                bkg { it.songs.transformLocalUri().let(player::addToPlaylist) }
                // Update the rank thumbnail to the newest one(to rank top 1).
                vm.runTaskUpdateRankItem(rankId, it.songs.first().oriCoverUrl, it.songs.size)
            } happenError {
                loge(it)
            } doWith this@RankDetailFragment
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.apply {
            if (rankMusic.value.isNull())
                runTaskFetchTopTrack(rankId)
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_songs),
                             songAdapter,
                             linearLayoutManager(),
                             listOf(actionBarBlankDecoration,
                                    VerticalItemDecorator(getDimen(R.dimen.md_one_half_unit).toInt())))
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        bottomSheet.apply {
            find<View>(R.id.ftv_download).setOnClickListener {
                if (::tempSongEntity.isInitialized) {
                    parent.requireStoragePermission {
                        val path = FilePathFactory.obtainMusicPath(tempSongEntity.encodeByName())
                        // Save into internal storage.
                        player.writeToFile(tempSongEntity.url, path)
                        vm.runTaskAddDownloadedTrackInfo(tempSongEntity, path)
                    }
                }
                dismiss()
            }
            find<View>(R.id.ftv_to_favorite).setOnClickListener {
                vm.runTaskAddToPlayHistory(tempSongEntity, PlaylistIndex.Favorite.ordinal)
                dismiss()
            }
            find<View>(R.id.ftv_music_info).setOnClickListener {
                dismiss()
            }
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_rank_detail

    override fun onDetach() {
        super.onDetach()
        player.clearPlaylist()
    }

    /**
     * Play a track by [MusicPlayer].
     *
     * @param parameter
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.RankTrackViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_PLAY_A_SONG)])
    fun playASong(parameter: AnyParameters) {
        val position = cast<Int>(parameter[PARAMS_LAYOUT_POSITION])
        val song = cast<SongEntity>(parameter[PARAMS_SONG_ENTITY])
        val res = player.play(position)
        if (res)
            songAdapter.playingPosition = position  // For updating current views are showing on the recycler view.
        else
            songAdapter.setStateMusicBy(position, res)
        // Add the play history into database.
        vm.runTaskAddToPlayHistory(song)
    }

    /**
     *
     * @param parameter
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.RankTrackViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_OPEN_BOTTOM_SHEET)])
    fun openBottomSheetDialog(parameter: AnyParameters) {
        tempSongEntity = cast(parameter[PARAMS_SONG_ENTITY])
        bottomSheet.show()
    }
}
