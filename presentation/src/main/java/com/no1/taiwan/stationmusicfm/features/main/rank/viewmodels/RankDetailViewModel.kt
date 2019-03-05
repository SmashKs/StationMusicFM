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

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdateRankItemCase
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdateRankItemReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.MusicInfoEntity
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RankDetailViewModel(
    private val fetchRankMusicCase: FetchRankMusicCase,
    private val updateRankItemCase: UpdateRankItemCase,
    private val mapperPool: PreziMapperPool
) : AutoViewModel() {
    private val _rankMusic by lazy { RespMutableLiveData<MusicInfoEntity.MusicEntity>() }
    val rankMusic: RespLiveData<MusicInfoEntity.MusicEntity> = _rankMusic
    private val topTracksMapper by lazy { cast<MusicPMapper>(mapperPool[MusicPMapper::class.java]) }

    fun runTaskFetchTopTrack(rankId: Int) = GlobalScope.launch {
        _rankMusic reqData { fetchRankMusicCase.execMapping(topTracksMapper, FetchRankMusicReq(RankParams(rankId))) }
    }

    fun runTaskUpdateRankItem(rankId: Int, topTrackUri: String, numOfTracks: Int) = GlobalScope.launch {
        updateRankItemCase.exec(UpdateRankItemReq(RankParams(rankId, topTrackUri, numOfTracks)))
    }
}
