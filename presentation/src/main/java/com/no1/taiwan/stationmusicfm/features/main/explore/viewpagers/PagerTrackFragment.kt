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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers

import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.consts.Pager
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_KEYWORD
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_MUSIC_BY_KEYWORD
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.kodein.di.generic.instance

class PagerTrackFragment : BasePagerFragment() {
    private var playingTrackPosition = DEFAULT_INT
    private var isSearching = false
    private val player: MusicPlayer by instance()

    init {
        BusFragLifeRegister(this)
    }

    override fun onResume() {
        super.onResume()
        if (scrollListener.fetchMoreBlock.isNull())
            scrollListener.fetchMoreBlock = ::fetchMore
    }

    override fun onDetach() {
        super.onDetach()
        // Release the function pointer.
        scrollListener.fetchMoreBlock = null
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        super.bindLiveData()
        observeNonNull(vm.tracksLiveData) {
            peel {
                if (enterCount <= 1 && it.tracks.isNotEmpty() && adapter.itemCount == 0)
                    adapter.append(cast<MusicVisitables>(it.tracks))
                // FIXME(jieyi): 2019-04-17 [vm.tracksLiveData] will be updated after fetching more.
                // After run fetch more function, [countShouldBe] will must be larger than current.
                val countShouldBe = Pager.LIMIT * it.attr.page.toInt()
                if (enterCount > 0 && it.attr.page.toInt() > 1 && countShouldBe > adapter.itemCount) {
                    adapter.append(cast<MusicVisitables>(it.tracks))
                }
            } doWith this@PagerTrackFragment
        }
        observeNonNull(vm.foundMusic) {
            if (this == null || !isSearching) return@observeNonNull
            adapter.playingPosition = playingTrackPosition
            adapter.reassignTrackUri(url)
            player.play(url)
            isSearching = false
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        initRecyclerViewWith(find(R.id.rv_hot_track), adapter, linearLayoutManager())
        find<RecyclerView>(R.id.rv_hot_track).apply {
            clearOnScrollListeners()
            addOnScrollListener(scrollListener)
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.viewpager_track

    /**
     * @param parameter artist name + track name.
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotTrackViewHolder.initView]
     */
    @Subscribe(tags = [Tag(PARAMS_SEARCH_MUSIC_BY_KEYWORD)])
    fun searchMusic(parameter: AnyParameters) {
        val keyword = cast<String>(parameter[PARAMS_SEARCH_KEYWORD])
        playingTrackPosition = cast(parameter[PARAMS_LAYOUT_POSITION])
        vm.runTaskSearchMusic(keyword)
        isSearching = true
    }

    private fun fetchMore() {
        vm.runTaskFetchHotTrack(searchArtistName)
    }
}
