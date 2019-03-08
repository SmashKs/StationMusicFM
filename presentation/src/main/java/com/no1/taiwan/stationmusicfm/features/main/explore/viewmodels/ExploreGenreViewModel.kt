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
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.TagParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopAlbumReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopTrackReq
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.TopAlbumsEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistsEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity.TagEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TracksTypeGenreEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TagPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.features.GenreMixInfo
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExploreGenreViewModel(
    private val fetchTagCase: FetchTagCase,
    private val fetchTagTopArtistCase: FetchTagTopArtistCase,
    private val fetchTagTopAlbumCase: FetchTagTopAlbumCase,
    private val fetchTagTopTrackCase: FetchTagTopTrackCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _tagLiveData by lazy { RespMutableLiveData<TagEntity>() }
    val tagLiveData: RespLiveData<TagEntity> = _tagLiveData
    private val _topArtistsLiveData by lazy { RespMutableLiveData<ArtistsEntity>() }
    val topArtistsLiveData: RespLiveData<ArtistsEntity> = _topArtistsLiveData
    private val _topAlbumsLiveData by lazy { RespMutableLiveData<TopAlbumsEntity>() }
    val topAlbumsLiveData: RespLiveData<TopAlbumsEntity> = _topAlbumsLiveData
    private val _topTracksLiveData by lazy { RespMutableLiveData<TracksTypeGenreEntity>() }
    val topTracksLiveData: RespLiveData<TracksTypeGenreEntity> = _topTracksLiveData
    private val _tagInfoLiveData by lazy { RespMutableLiveData<GenreMixInfo>() }
    val tagInfoLiveData: RespLiveData<GenreMixInfo> = _tagInfoLiveData
    private val tagMapper by lazy { digMapper(TagPMapper::class) }
    private val artistsMapper by lazy { digMapper(ArtistsPMapper::class) }
    private val albumsMapper by lazy { digMapper(TopAlbumTypeGenrePMapper::class) }
    private val tracksMapper by lazy { digMapper(TracksTypeGenrePMapper::class) }

    fun runTaskFetchGenreInfo(tagName: String) = GlobalScope.launch {
        val parameters = TagParams(tagName)
        _tagInfoLiveData reqData {
            val tag = fetchTagCase.execMapping(tagMapper, FetchTagReq(parameters))
//            val artist = fetchTagTopArtistCase.execMapping(artistsMapper, FetchTagTopArtistReq(parameters))
            val album = fetchTagTopAlbumCase.execMapping(albumsMapper, FetchTagTopAlbumReq(parameters))
            val tracks = fetchTagTopTrackCase.execMapping(tracksMapper, FetchTagTopTrackReq(parameters))
            // If success to get them assign to each livedata.
            _tagLiveData.postValue(ResponseState.Success(tag))
//            _topArtistsLiveData.postValue(ResponseState.Success(artist))
            _topAlbumsLiveData.postValue(ResponseState.Success(album))
            _topTracksLiveData.postValue(ResponseState.Success(tracks))

            GenreMixInfo(tag, ArtistsEntity(), album, tracks)
        }
    }
}
