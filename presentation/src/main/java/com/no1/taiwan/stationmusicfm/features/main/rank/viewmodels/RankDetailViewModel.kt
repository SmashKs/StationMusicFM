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

import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams
import com.no1.taiwan.stationmusicfm.domain.usecases.AddLocalMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddLocalMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdateRankItemCase
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdateRankItemReq
import com.no1.taiwan.stationmusicfm.entities.mappers.MusicToParamsMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.MusicInfoEntity.MusicEntity
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class RankDetailViewModel(
    private val fetchRankMusicCase: FetchRankMusicCase,
    private val updateRankItemCase: UpdateRankItemCase,
    private val addLocalMusicCase: AddLocalMusicCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _rankMusic by lazy { RespMutableLiveData<MusicEntity>() }
    val rankMusic: RespLiveData<MusicEntity> = _rankMusic
    private val topTracksMapper by lazy { digMapper(MusicPMapper::class) }

    fun runTaskFetchTopTrack(rankId: Int) = launchBehind {
        _rankMusic reqData { fetchRankMusicCase.execMapping(topTracksMapper, FetchRankMusicReq(RankParams(rankId))) }
    }

    fun runTaskUpdateRankItem(rankId: Int, topTrackUri: String, numOfTracks: Int) = launchBehind {
        updateRankItemCase.exec(UpdateRankItemReq(RankParams(rankId, topTrackUri, numOfTracks)))
    }

    fun runTaskAddToPlayHistory(song: SongEntity) = launchBehind {
        addLocalMusicCase.exec(AddLocalMusicReq(MusicToParamsMapper().toParamsFrom(song)))
    }

    fun runTaskAddDownloadedTrackInfo(song: SongEntity, localUri: String) = launchBehind {
        val parameter = MusicToParamsMapper()
            .toParamsFrom(song)
            .copy(hasOwn = true, localTrackUri = localUri)
        addLocalMusicCase.exec(AddLocalMusicReq(parameter))
    }
}
