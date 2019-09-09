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

package com.no1.taiwan.stationmusicfm.features.main.rank.viewmodels

import com.no1.taiwan.stationmusicfm.domain.ResponseState
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicRanksCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicRanksReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankIdsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankIdsReq
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.BriefRankPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.others.RankingPMapper
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.features.RankingIds
import com.no1.taiwan.stationmusicfm.features.RankingIdsForChart
import com.no1.taiwan.stationmusicfm.features.Rankings
import com.no1.taiwan.stationmusicfm.ktx.aac.livedata.TransformedLiveData
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execListMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class RankIndexViewModel(
    private val fetchRankIdsCase: FetchRankIdsCase,
    private val fetchMusicRanksCase: FetchMusicRanksCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _rankIds by lazy { RespMutableLiveData<RankingIds>() }
    private val _topRanks by lazy { RespMutableLiveData<Rankings>() }
    val rankTopper = RankingIdLiveData(_topRanks)
    val rankElse = RankingIdForChartLiveData(_topRanks)
    //region Mappers
    private val rankingIdMapper by lazy { digMapper(RankingPMapper::class) }
    private val briefRankMapper by lazy { digMapper(BriefRankPMapper::class) }
    //endregion

    @Deprecated("This api was broken.")
    fun runTaskFetchRankIds() = launchBehind {
        _rankIds reqData { fetchRankIdsCase.execListMapping(rankingIdMapper, FetchRankIdsReq()) }
    }

    fun runTaskFetchMusicRanks() = launchBehind {
        _topRanks reqData {
            fetchMusicRanksCase.execListMapping(briefRankMapper,
                                                FetchMusicRanksReq())
        }
    }

    class RankingIdForChartLiveData(
        override val source: RespMutableLiveData<Rankings>
    ) : TransformedLiveData<ResponseState<Rankings>, RankingIdsForChart>() {
        override fun getTransformed(source: ResponseState<Rankings>) =
            if (source is ResponseState.Success<Rankings>)
                source.data?.let { it.subList(4, it.size - 1) }
                    ?.map {
                        RankingIdForChartItem(it.rankId, it.title, it.subTitle, it.coverUrl, 0)
                    }.orEmpty()
            else
                emptyList()
    }

    class RankingIdLiveData(
        override val source: RespMutableLiveData<Rankings>
    ) : TransformedLiveData<ResponseState<Rankings>, RankingIds>() {
        override fun getTransformed(source: ResponseState<Rankings>) =
            if (source is ResponseState.Success<Rankings>)
                source.data?.subList(0, 4)?.map {
                    RankingIdEntity(it.rankId, it.title, it.subTitle, it.coverUrl, 0)
                }
            else
                emptyList()
    }
}
