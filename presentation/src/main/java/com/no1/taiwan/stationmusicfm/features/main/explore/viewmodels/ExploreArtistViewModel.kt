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

import androidx.lifecycle.MutableLiveData
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.ResponseState
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.ArtistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarArtistReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsSimilarPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksWithStreamablePMapper
import com.no1.taiwan.stationmusicfm.features.ArtistMixInfo
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExploreArtistViewModel(
    private val fetchArtistCase: FetchArtistCase,
    private val fetchArtistTopAlbumCase: FetchArtistTopAlbumCase,
    private val fetchSimilarArtistCase: FetchSimilarArtistCase,
    private val fetchArtistTopTrackCase: FetchArtistTopTrackCase,
    private val mapperPool: PreziMapperPool
) : AutoViewModel() {
    private val _artistLiveData by lazy { RespMutableLiveData<ArtistInfoEntity.ArtistEntity>() }
    val artistLiveData: RespLiveData<ArtistInfoEntity.ArtistEntity> = _artistLiveData
    private val _albumsLiveData by lazy { RespMutableLiveData<AlbumInfoEntity.TopAlbumsEntity>() }
    val albumsLiveData: RespLiveData<AlbumInfoEntity.TopAlbumsEntity> = _albumsLiveData
    private val _similarArtistsLiveData by lazy { RespMutableLiveData<ArtistInfoEntity.ArtistsSimilarEntity>() }
    val similarArtistsLiveData: RespLiveData<ArtistInfoEntity.ArtistsSimilarEntity> = _similarArtistsLiveData
    private val _tracksLiveData by lazy { RespMutableLiveData<TrackInfoEntity.TracksWithStreamableEntity>() }
    val tracksLiveData: RespLiveData<TrackInfoEntity.TracksWithStreamableEntity> = _tracksLiveData
    private val _artistInfoLiveData by lazy { RespMutableLiveData<ArtistMixInfo>() }
    val artistInfoLiveData: RespLiveData<ArtistMixInfo> = _artistInfoLiveData
    private val artistMapper by lazy { cast<ArtistPMapper>(mapperPool[ArtistPMapper::class.java]) }
    private val topAlbumPMapper by lazy { cast<TopAlbumPMapper>(mapperPool[TopAlbumPMapper::class.java]) }
    private val artistsSimilarPMapper by lazy { cast<ArtistsSimilarPMapper>(mapperPool[ArtistsSimilarPMapper::class.java]) }
    private val tracksWithStreamablePMapper by lazy { cast<TracksWithStreamablePMapper>(mapperPool[TracksWithStreamablePMapper::class.java]) }

    private val _lastFindMbid by lazy { MutableLiveData<String>() }
    val lastFindMbid get() = _lastFindMbid.value

    fun runTaskFetchArtistInfo(mbid: String, name: String) = GlobalScope.launch {
        val parameters = ArtistParams(mbid, name)
        _artistInfoLiveData reqData {
            val artist = fetchArtistCase.execMapping(artistMapper, FetchArtistReq(parameters))
            val album = fetchArtistTopAlbumCase.execMapping(topAlbumPMapper, FetchArtistTopAlbumReq(parameters))
            val similarArtist =
                fetchSimilarArtistCase.execMapping(artistsSimilarPMapper, FetchSimilarArtistReq(parameters))
            val tracks =
                fetchArtistTopTrackCase.execMapping(tracksWithStreamablePMapper, FetchArtistTopTrackReq(parameters))
            // If success to get them assign to each livedata.
            _artistLiveData.postValue(ResponseState.Success(artist))
            _albumsLiveData.postValue(ResponseState.Success(album))
            _similarArtistsLiveData.postValue(ResponseState.Success(similarArtist))
            _tracksLiveData.postValue(ResponseState.Success(tracks))
            _lastFindMbid.postValue(mbid)

            ArtistMixInfo(artist, album, similarArtist, tracks)
        }
    }
}
