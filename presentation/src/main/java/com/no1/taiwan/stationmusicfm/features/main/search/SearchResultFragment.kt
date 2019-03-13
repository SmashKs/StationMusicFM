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
import com.devrapid.kotlinknifer.obtainViewStub
import com.devrapid.kotlinknifer.showViewStub
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.search.viewmodels.SearchViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_SEPARATOR
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TRACK_URI
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_PLAY_A_SONG
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLongerLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SearchResultFragment : AdvFragment<MainActivity, SearchViewModel>(), SearchCommonOperations {
    override val viewmodelProviderSource get() = PROVIDER_FROM_CUSTOM_FRAGMENT
    override val viewmodelProviderFragment get() = requireParentFragment()
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val adapter: MusicAdapter by instance()
    private val decoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_SEPARATOR)
    private val actionBarBlankDecoration: RecyclerView.ItemDecoration by instance(ITEM_DECORATION_ACTION_BAR_BLANK)
    private val player: MusicPlayer by instance()
    private var isFirstComing = true
    private val rvScrollListener = object : RecyclerView.OnScrollListener() {
        /** The total number of items in the data set after the last load. */
        private var previousTotal = 0
        /** True if we are still waiting for the last set of data to load. */
        private var isLoading = true
        private val visibleThreshold = 3

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val visibleItemCount = recyclerView.childCount
            val totalItemCount = requireNotNull(recyclerView.layoutManager).itemCount
            val firstVisibleItem = cast<LinearLayoutManager>(recyclerView.layoutManager).findFirstVisibleItemPosition()

            if (isLoading) {
                if (totalItemCount > previousTotal) {
                    isLoading = false
                    previousTotal = totalItemCount
                }
            }
            if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                // End has been reached
                // NOTE(jieyi): totalItemCount is set for avoiding the first page to load again and again.
                //  If there's only few items(1 ~ 7) will trigger load more in the beginning.
                vm.runTaskSearchMusic(vm.keyword.value.orEmpty())
                isLoading = true
            }
        }
    }

    init {
        BusFragLongerLifeRegister(this)
        SearchShowingLifeRegister(this)
    }

    override fun onResume() {
        super.onResume()
        parent.onQuerySubmit = { vm.runTaskAddHistory(it) }
    }

    override fun onDetach() {
        super.onDetach()
        // Reset the page number for searching again.
        vm.resetPageNumber()
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.musics) {
            peel {
                if (it.items.isEmpty()) {
                    switchStub(false)
                    return@peel
                }
                switchStub(true)
                adapter.append(cast<MusicVisitables>(it.items))
                vm.increasePageNumber()
                if (isFirstComing) {
                    initRecyclerViewWith(find(R.id.v_result),
                                         adapter,
                                         linearLayoutManager(),
                                         listOf(decoration, actionBarBlankDecoration))
                    find<RecyclerView>(R.id.v_result).apply {
                        clearOnScrollListeners()
                        addOnScrollListener(rvScrollListener)
                    }
                    isFirstComing = false
                }
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
        val uri = cast<String>(parameter[PARAMS_TRACK_URI])
        val position = cast<Int>(parameter[PARAMS_LAYOUT_POSITION])

        player.play(uri)
        find<RecyclerView>(R.id.v_result).apply {
            // FIXME(jieyi): 2019-03-13 childCount is the count only showing on the view,
            //  but there're few views haven't been recycled which doesn't change.
            repeat(childCount) {
                cast<Notifiable>(getChildViewHolder(getChildAt(it))).notifyChange(position)
            }
        }
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
