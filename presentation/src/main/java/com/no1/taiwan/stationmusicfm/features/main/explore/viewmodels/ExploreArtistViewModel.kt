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
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.ArtistParams
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.SrchSongParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistPhotoCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistPhotoReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarArtistReq
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.TopAlbumsEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistsSimilarEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.PhotosEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TracksWithStreamableEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPhotosPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsSimilarPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksWithStreamablePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.MusicInfoEntity.MusicEntity
import com.no1.taiwan.stationmusicfm.ext.consts.Pager
import com.no1.taiwan.stationmusicfm.features.ArtistMixInfo
import com.no1.taiwan.stationmusicfm.ktx.acc.livedata.TransformedLiveData
import com.no1.taiwan.stationmusicfm.utils.aac.data
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class ExploreArtistViewModel(
    private val fetchArtistCase: FetchArtistCase,
    private val fetchArtistTopAlbumCase: FetchArtistTopAlbumCase,
    private val fetchSimilarArtistCase: FetchSimilarArtistCase,
    private val fetchArtistTopTrackCase: FetchArtistTopTrackCase,
    private val fetchArtistPhotoCase: FetchArtistPhotoCase,
    private val fetchMusicCase: FetchMusicCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _artistLiveData by lazy { NotifMutableLiveData<ArtistEntity>() }
    val artistLiveData: NotifLiveData<ArtistEntity> = _artistLiveData
    private val _albumsLiveData by lazy { NotifMutableLiveData<TopAlbumsEntity>() }
    val albumsLiveData: NotifLiveData<TopAlbumsEntity> = _albumsLiveData
    private val _similarArtistsLiveData by lazy { NotifMutableLiveData<ArtistsSimilarEntity>() }
    val similarArtistsLiveData: NotifLiveData<ArtistsSimilarEntity> = _similarArtistsLiveData
    private val _tracksLiveData by lazy { NotifMutableLiveData<TracksWithStreamableEntity>() }
    val tracksLiveData: NotifLiveData<TracksWithStreamableEntity> = _tracksLiveData
    // Mixed all information (artist, album, similar artist, tracks) into one data.
    private val _artistInfoLiveData by lazy { RespMutableLiveData<ArtistMixInfo>() }
    val artistInfoLiveData: RespLiveData<ArtistMixInfo> = _artistInfoLiveData
    private val _photosLiveData by lazy { RespMutableLiveData<PhotosEntity>() }
    val photosLiveData: RespLiveData<PhotosEntity> = _photosLiveData
    // For playing a song by another usecase to find the track uri.
    private val _musics by lazy { NotifMutableLiveData<MusicEntity>() }
    val foundMusic by lazy { MusicLiveData(_musics) }
    //region Mappers
    private val artistMapper by lazy { digMapper(ArtistPMapper::class) }
    private val topAlbumMapper by lazy { digMapper(TopAlbumPMapper::class) }
    private val artistsSimilarMapper by lazy { digMapper(ArtistsSimilarPMapper::class) }
    private val tracksWithStreamableMapper by lazy { digMapper(TracksWithStreamablePMapper::class) }
    private val photosMapper by lazy { digMapper(ArtistPhotosPMapper::class) }
    private val musicMapper by lazy { digMapper(MusicPMapper::class) }
    //endregion

    fun runTaskFetchArtistInfo(mbid: String, name: String) = launchBehind {
        val parameters = ArtistParams(mbid, name)
        _artistInfoLiveData reqData {
            val artist = fetchArtistCase.execMapping(artistMapper, FetchArtistReq(parameters))
            val album = fetchArtistTopAlbumCase.execMapping(topAlbumMapper, FetchArtistTopAlbumReq(parameters))
            val similarArtist =
                fetchSimilarArtistCase.execMapping(artistsSimilarMapper, FetchSimilarArtistReq(parameters))
            val tracks =
                fetchArtistTopTrackCase.execMapping(tracksWithStreamableMapper, FetchArtistTopTrackReq(parameters))
            val photos = fetchArtistPhotoCase.execMapping(photosMapper,
                                                          FetchArtistPhotoReq(ArtistParams(artistName = name)))
            // If success to get them assign to each livedata.
            _artistLiveData.postValue(ResponseState.Success(artist))
            _albumsLiveData.postValue(ResponseState.Success(album))
            _similarArtistsLiveData.postValue(ResponseState.Success(similarArtist))
            _tracksLiveData.postValue(ResponseState.Success(tracks))
            _photosLiveData.postValue(ResponseState.Success(photos))

            ArtistMixInfo(artist, album, similarArtist, tracks, photos)
        }
    }

    fun runTaskFetchArtistPhoto(artistName: String) = launchBehind {
        _photosLiveData reqData {
            fetchArtistPhotoCase.execMapping(photosMapper, FetchArtistPhotoReq(ArtistParams(artistName = artistName)))
        }
    }

    fun runTaskFetchHotAlbum(name: String, limit: Int = Pager.LIMIT) = launchBehind {
        val parameter = _albumsLiveData.data()?.attr?.let {
            autoIncreaseParams(it, limit)?.let {
                ArtistParams(artistName = name).apply {
                    page = it.page
                }
            }
        }
        _albumsLiveData reqData {
            fetchArtistTopAlbumCase.execMapping(topAlbumMapper, FetchArtistTopAlbumReq(parameter!!))
        }
    }

    fun runTaskFetchHotTrack(name: String, limit: Int = Pager.LIMIT) = launchBehind {
        val parameter = _tracksLiveData.data()?.attr?.let {
            autoIncreaseParams(it, limit)?.let {
                ArtistParams(artistName = name).apply {
                    page = it.page
                }
            }
        }
        _tracksLiveData reqData {
            fetchArtistTopTrackCase.execMapping(tracksWithStreamableMapper, FetchArtistTopTrackReq(parameter!!))
        }
    }

    fun runTaskFetchSimilarArtist(name: String, limit: Int = Pager.LIMIT) = launchBehind {
        val parameter = _similarArtistsLiveData.data()?.attr?.let {
            autoIncreaseParams(it, limit)?.let {
                ArtistParams(artistName = name).apply {
                    page = it.page
                }
            }
        }
        _similarArtistsLiveData reqData {
            fetchSimilarArtistCase.execMapping(artistsSimilarMapper, FetchSimilarArtistReq(parameter!!))
        }
    }

    fun runTaskSearchMusic(wholeKeyword: String) = launchBehind {
        _musics reqData { fetchMusicCase.execMapping(musicMapper, FetchMusicReq(SrchSongParams(wholeKeyword))) }
    }

    class MusicLiveData(
        override val source: NotifMutableLiveData<MusicEntity>
    ) : TransformedLiveData<ResponseState<MusicEntity>, SongEntity?>() {
        override fun getTransformed(source: ResponseState<MusicEntity>): SongEntity? {
            return if (source is ResponseState.Success<MusicEntity>)
                source.data?.items?.firstOrNull()
            else
                null
        }
    }
}
