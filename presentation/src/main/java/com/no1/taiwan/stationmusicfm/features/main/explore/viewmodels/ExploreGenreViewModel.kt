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

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.TagParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopAlbumReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopArtistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopTrackReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TagPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksPMapper
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
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
    private val mapperPool: PreziMapperPool
) : AutoViewModel() {
    private val _tagLiveData by lazy { RespMutableLiveData<TagInfoEntity.TagEntity>() }
    val tagLiveData: RespLiveData<TagInfoEntity.TagEntity> = _tagLiveData
    private val _topArtistsLiveData by lazy { RespMutableLiveData<ArtistInfoEntity.ArtistsEntity>() }
    val topArtistsLiveData: RespLiveData<ArtistInfoEntity.ArtistsEntity> = _topArtistsLiveData
    private val _topAlbumsLiveData by lazy { RespMutableLiveData<AlbumInfoEntity.TopAlbumsEntity>() }
    val topAlbumsLiveData: RespLiveData<AlbumInfoEntity.TopAlbumsEntity> = _topAlbumsLiveData
    private val _topTracksLiveData by lazy { RespMutableLiveData<TrackInfoEntity.TracksEntity>() }
    val topTracksLiveData: RespLiveData<TrackInfoEntity.TracksEntity> = _topTracksLiveData
    private val tagMapper by lazy { cast<TagPMapper>(mapperPool[TagPMapper::class.java]) }
    private val artistsMapper by lazy { cast<ArtistsPMapper>(mapperPool[ArtistsPMapper::class.java]) }
    private val albumsMapper by lazy { cast<TopAlbumPMapper>(mapperPool[TopAlbumPMapper::class.java]) }
    private val tracksMapper by lazy { cast<TracksPMapper>(mapperPool[TracksPMapper::class.java]) }

    fun runTaskFetchTag(name: String) = GlobalScope.launch {
        _tagLiveData reqData { fetchTagCase.execMapping(tagMapper, FetchTagReq(TagParams(name))) }
    }

    fun runTaskFetchTopArtist(name: String) = GlobalScope.launch {
        _topArtistsLiveData reqData {
            fetchTagTopArtistCase.execMapping(artistsMapper, FetchTagTopArtistReq(TagParams(name)))
        }
    }

    fun runTaskFetchTopAlbum(name: String) = GlobalScope.launch {
        _topAlbumsLiveData reqData {
            fetchTagTopAlbumCase.execMapping(albumsMapper, FetchTagTopAlbumReq(TagParams(name)))
        }
    }

    fun runTaskFetchTopTrack(name: String) = GlobalScope.launch {
        _topTracksLiveData reqData {
            fetchTagTopTrackCase.execMapping(tracksMapper, FetchTagTopTrackReq(TagParams(name)))
        }
    }
}
