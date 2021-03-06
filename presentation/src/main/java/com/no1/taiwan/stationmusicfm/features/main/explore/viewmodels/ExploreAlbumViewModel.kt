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

import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.AlbumParams
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.ArtistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchAlbumReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistReq
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.AlbumEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPMapper
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class ExploreAlbumViewModel(
    private val fetchAlbumCase: FetchAlbumCase,
    private val fetchArtistCase: FetchArtistCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _albumLiveData by lazy { NotifMutableLiveData<AlbumEntity>() }
    val albumLiveData: NotifLiveData<AlbumEntity> = _albumLiveData
    private val _artistLiveData by lazy { NotifMutableLiveData<ArtistEntity>() }
    val artistLiveData: NotifLiveData<ArtistEntity> = _artistLiveData
    //region Mappers
    private val albumMapper by lazy { digMapper(AlbumPMapper::class) }
    private val artistMapper by lazy { digMapper(ArtistPMapper::class) }
    //endregion

    fun runTaskFetchAlbum(mbid: String, albumName: String, artistName: String) =
        launchBehind {
            _albumLiveData reqData {
                fetchAlbumCase.execMapping(albumMapper,
                                           FetchAlbumReq(AlbumParams(mbid, albumName, artistName)))
            }
        }

    fun runTaskFetchArtist(artistName: String) = launchBehind {
        _artistLiveData reqData {
            fetchArtistCase.execMapping(artistMapper,
                                        FetchArtistReq(ArtistParams(artistName = artistName)))
        }
    }
}
