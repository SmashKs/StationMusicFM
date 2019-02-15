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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.rank.viewmodels.RankIndexViewModel
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance

class RankIndexFragment : AdvFragment<MainActivity, RankIndexViewModel>() {
    private val topper2GridLayoutManager: GridLayoutManager by instance(null, 2)
    private val chart2GridLayoutManager: GridLayoutManager by instance(null, 2)
    private val topperAdapter: MusicAdapter by instance()
    private val chartAdapter: MusicAdapter by instance()

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.rankIds) {
            peel {
                if (it.isEmpty()) return@peel
                topperAdapter.appendList(cast(it.subList(0, 4).toMutableList()))
                chartAdapter.appendList(cast(it.subList(4, it.size - 1).map {
                    RankingIdForChartItem(it.id, it.title, it.update, it.topTrackUri, it.trackNumber)
                }))
            } happenError {
                loge(it)
            } doWith this@RankIndexFragment
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
            if (rankIds.value.isNull())
                runTaskFetchRankIds()
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        find<RecyclerView>(R.id.rv_topper).apply {
            if (layoutManager.isNull())
                layoutManager = topper2GridLayoutManager
            if (adapter.isNull())
                adapter = topperAdapter
        }
        find<RecyclerView>(R.id.rv_chart).apply {
            if (layoutManager.isNull())
                layoutManager = chart2GridLayoutManager
            if (adapter.isNull())
                adapter = chartAdapter
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_rank_index
}
