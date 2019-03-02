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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.rank.viewmodels.RankIndexViewModel
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_TITLE
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_RANK_ID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_RANK_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class RankIndexFragment : IndexFragment<RankIndexViewModel>() {
    private val gridLayoutManager: () -> GridLayoutManager by provider(null, 2)
    private val topperAdapter: MusicAdapter by instance()
    private val chartAdapter: MusicAdapter by instance()

    init {
        BusFragLifeRegister(this)
    }

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
        initRecyclerViewWith(find(R.id.rv_chart), chartAdapter, gridLayoutManager())
        initRecyclerViewWith(find(R.id.rv_topper), topperAdapter, gridLayoutManager())
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_rank_index

    /**
     * @event_from[com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.ChartViewHolder.initView]
     * @event_from[com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.TopperViewHolder.initView]
     * @param params
     */
    @Subscribe(tags = [Tag(TAG_RANK_DETAIL)])
    fun gotoDetailFragment(params: AnyParameters) {
        val rankId = cast<Int>(params[PARAMS_TO_RANK_ID])
        val titleOfChart = cast<String>(params[PARAMS_COMMON_TITLE])

        findNavController().navigate(R.id.action_frag_rank_index_to_frag_rank_detail,
                                     RankDetailFragment.createBundle(rankId, titleOfChart))
    }
}
