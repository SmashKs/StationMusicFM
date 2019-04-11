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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels

import com.no1.taiwan.stationmusicfm.domain.ResponseState
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.TrackParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarTrackReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTrackReq
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TracksEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TrackPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksPMapper
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class ExploreTrackViewModel(
    private val fetchTrackCase: FetchTrackCase,
    private val fetchSimilarTrackCase: FetchSimilarTrackCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _trackLiveData by lazy { RespMutableLiveData<TrackEntity>() }
    val trackLiveData: RespLiveData<TrackEntity> = _trackLiveData
    private val _similarTracksLiveData by lazy { RespMutableLiveData<TracksEntity>() }
    val similarTracksLiveData: RespLiveData<TracksEntity> = _similarTracksLiveData
    private val _trackInfoLiveData by lazy { RespMutableLiveData<Pair<TrackEntity, TracksEntity>>() }
    val trackInfoLiveData: RespLiveData<Pair<TrackEntity, TracksEntity>> = _trackInfoLiveData
    //region Mappers
    private val trackMapper by lazy { digMapper(TrackPMapper::class) }
    private val tracksMapper by lazy { digMapper(TracksPMapper::class) }
    //endregion

    fun runTaskFetchTrack(mbid: String, artistName: String, trackName: String) = launchBehind {
        val parameters = TrackParams(mbid, trackName, artistName)
        _trackInfoLiveData reqData {
            val track = fetchTrackCase.execMapping(trackMapper, FetchTrackReq(parameters))
            val similarTracks = fetchSimilarTrackCase.execMapping(tracksMapper, FetchSimilarTrackReq(parameters))
            _trackLiveData.postValue(ResponseState.Success(track))
            _similarTracksLiveData.postValue(ResponseState.Success(similarTracks))

            Pair(track, similarTracks)
        }
    }
}
