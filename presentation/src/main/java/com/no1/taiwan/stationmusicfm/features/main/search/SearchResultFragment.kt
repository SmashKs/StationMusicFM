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

package com.no1.taiwan.stationmusicfm.features.main.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.gone
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.obtainViewStub
import com.devrapid.kotlinknifer.showViewStub
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.search.viewmodels.SearchViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_SEPARATOR
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.BottomSheetFactory
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.assignDownloadIcon
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.NotifiableAdapter
import com.no1.taiwan.stationmusicfm.kits.recyclerview.scrolllisteners.LoadMoreScrollListener
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.player.PlayerEventListener
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SONG_ENTITY
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_OPEN_BOTTOM_SHEET
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_PLAY_A_SONG
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_SAVING_PLAYLIST
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLongerLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.entity.transformLocalUri
import com.no1.taiwan.stationmusicfm.utils.file.FilePathFactory
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SearchResultFragment : AdvFragment<MainActivity, SearchViewModel>(), SearchCommonOperations {
    override val viewmodelProviderSource get() = PROVIDER_FROM_CUSTOM_FRAGMENT
    override val viewmodelProviderFragment get() = requireParentFragment()
    private var isFirstComing = true
    private lateinit var tempSongEntity: SongEntity
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val adapter: NotifiableAdapter by instance(ADAPTER_TRACK)
    private val decoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_SEPARATOR)
    private val actionBarBlankDecoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_ACTION_BAR_BLANK)
    private val rvScrollListener: LoadMoreScrollListener by instance()
    private val player: MusicPlayer by instance()
    private val optionsBottomSheet by lazy { BottomSheetFactory.createMusicSheet(parent, viewLifecycleOwner) }

    init {
        BusFragLongerLifeRegister(this)
        SearchShowingLifeRegister(this)
    }

    override fun onResume() {
        super.onResume()
        if (rvScrollListener.fetchMoreBlock.isNull())
            rvScrollListener.fetchMoreBlock = ::fetchMore
        parent.onQuerySubmit = {
            vm.resetPageNumber()
            vm.runTaskAddHistory(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Reset the page number for searching again.
        vm.resetPageNumber()
        // Release the function pointer.
        rvScrollListener.fetchMoreBlock = null
        player.apply {
            clearPlaylist()
            setEventListener(null)
        }
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.musics) {
            peel {
                if (it.items.isEmpty() && vm.getCurPageNumber() == 0) {
                    switchStub(false)
                    return@peel
                }
                // Show the view stub.
                switchStub(true)
                // When search in the beginning, adapter whole list will be replaced.
                if (vm.getCurPageNumber() == 0) {
                    adapter.replaceWholeList(cast(it.items))
                    player.clearPlaylist()
                }
                else
                    adapter.append(cast<MusicVisitables>(it.items))
                it.items.transformLocalUri().let(player::addToPlaylist)
                vm.increasePageNumber()  // after success fetching, auto increasing the page.
                fetchAgainForFirstTime()
                // Initialize the recycler view.
                initRecyclerView()
            } happenError {
                loge(it)
            } doWith this@SearchResultFragment
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.runTaskSearchMusic(vm.keyword.value.orEmpty())
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        super.componentListenersBinding()
        optionsBottomSheet.onDownloaded = {
            vm.runTaskAddDownloadedTrackInfo(tempSongEntity, it)
        }
        player.setEventListener(PlayerEventListener {
            onChangeTrack = listener@{ oldIndex, newIndex ->
                // In the beginning, avoid changing play position multiple times.
                if (oldIndex == DEFAULT_INT) return@listener
                if (adapter.retrieveTrackUri(oldIndex) != player.curPlayingUri)
                    adapter.playingPosition = newIndex
            }
        })
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_search_result

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // It needs to reset again when phone rotated the screen.
        isFirstComing = true
    }

    override fun keepKeywordIntoViewModel(keyword: String) = vm.keyword.postValue(keyword)

    override fun getKeptKeyword() = vm.keyword.value.orEmpty()

    fun searchMusicBy(keyword: String) = vm.runTaskSearchMusic(keyword)

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
            adapter.playingPosition = position  // For updating current views are showing on the recycler view.
        else
            adapter.setStateMusicBy(position, res)
        // Add the play history into database.
        vm.runTaskAddOrUpdateToPlayHistory(song)
    }

    /**
     * @param parameter
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.RankTrackViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_OPEN_BOTTOM_SHEET)])
    fun openBottomSheetDialog(parameter: AnyParameters) {
        tempSongEntity = cast(parameter[PARAMS_SONG_ENTITY])
        optionsBottomSheet.run {
            songEntity = tempSongEntity
            assignDownloadIcon(FilePathFactory.getMusicPath(tempSongEntity.encodeByName()).isNull())
        }.show()
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.kits.bottomsheet.viewholders.BottomPlaylistViewHolder.initView]
     */
    @Subscribe(tags = [Tag(TAG_SAVING_PLAYLIST)])
    fun keepHistory(playlistId: Number) {
        vm.runTaskAddOrUpdateToPlayHistory(tempSongEntity, playlistId.toInt())
        optionsBottomSheet.dismissPlaylist()
    }

    private fun fetchAgainForFirstTime() {
        // The first time searching will fetch two times in the beginning.
        if (vm.getCurPageNumber() == 1)
            vm.runTaskSearchMusic(vm.keyword.value.orEmpty())
    }

    private fun initRecyclerView() {
        if (isFirstComing) {
            initRecyclerViewWith(find(R.id.v_result),
                                 adapter,
                                 linearLayoutManager(),
                                 listOf(decoration, actionBarBlankDecoration))
            isFirstComing = false
        }
        // Reset the listener again when search again on this [SearchResultFragment].
        if (vm.getCurPageNumber() == 1) {
            find<RecyclerView>(R.id.v_result).apply {
                clearOnScrollListeners()
                logw("??????????????????????????????????????")
                addOnScrollListener(rvScrollListener)
            }
        }
    }

    private fun fetchMore() {
        // NOTE: totalItemCount is set for avoiding that the first page to load again and again.
        //  If there's only few items(1 ~ 7) will trigger load more in the beginning.
        if (vm.getCurPageNumber() > 1)
            vm.runTaskSearchMusic(vm.keyword.value.orEmpty())
    }

    private fun switchStub(isResult: Boolean) {
        if (isResult) {
            showViewStub(R.id.vs_result, R.id.v_result)
            obtainViewStub(R.id.vs_no_result, R.id.v_no_result).gone()
        }
        else {
            showViewStub(R.id.vs_no_result, R.id.v_no_result)
            obtainViewStub(R.id.vs_result, R.id.v_result).gone()
        }
    }
}
