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

package com.no1.taiwan.stationmusicfm.features.main.explore

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopArtistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTrackReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksPMapper
import com.no1.taiwan.stationmusicfm.ext.consts.Pager
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val fetchChartTopTrackCase: FetchChartTopTrackCase,
    private val fetchChartTopArtistCase: FetchChartTopArtistCase,
    private val mapperPool: PreziMapperPool
) : AutoViewModel() {
    private val _topTracks by lazy { RespMutableLiveData<TrackInfoEntity.TracksEntity>() }
    val topTracks: RespLiveData<TrackInfoEntity.TracksEntity> = _topTracks
    private val _topArtists by lazy { RespMutableLiveData<ArtistInfoEntity.ArtistsEntity>() }
    val topArtists: RespLiveData<ArtistInfoEntity.ArtistsEntity> = _topArtists
    private val topTracksMapper by lazy { cast<TracksPMapper>(mapperPool[TracksPMapper::class.java]) }
    private val topArtistsMapper by lazy { cast<ArtistsPMapper>(mapperPool[ArtistsPMapper::class.java]) }

    fun runTaskFetchTopTrack(limit: Int = Pager.LIMIT) = GlobalScope.launch {
        var params = FetchChartTopTrackReq()
        _topTracks.value?.data?.attr?.let {
            params = FetchChartTopTrackReq(autoIncreaseParams(it, limit) ?: return@launch)
        }
        _topTracks reqData { fetchChartTopTrackCase.execMapping(topTracksMapper, params) }
    }

    fun runTaskFetchTopArtist(limit: Int = Pager.LIMIT) = GlobalScope.launch {
        var params = FetchChartTopArtistReq()
        _topArtists.value?.data?.attr?.let {
            params = FetchChartTopArtistReq(autoIncreaseParams(it, limit) ?: return@launch)
        }
        _topArtists reqData { fetchChartTopArtistCase.execMapping(topArtistsMapper, params) }
    }
}
