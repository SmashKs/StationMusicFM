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

import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_KEYWORD
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_MUSIC_BY_KEYWORD
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import org.jetbrains.anko.support.v4.find
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
        adapter.notifyDataSetChanged()
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        super.bindLiveData()
        observeNonNull(vm.tracksLiveData) {
            peel {
                adapter.replaceWholeList(cast(it.tracks))
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
}
