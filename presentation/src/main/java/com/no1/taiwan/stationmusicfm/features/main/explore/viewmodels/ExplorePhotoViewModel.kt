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

import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.ArtistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistPhotoCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistPhotoReq
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPhotosPMapper
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class ExplorePhotoViewModel(
    private val fetchArtistPhotoCase: FetchArtistPhotoCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _photosLiveData by lazy { RespMutableLiveData<ArtistInfoEntity.PhotosEntity>() }
    val photosLiveData: RespLiveData<ArtistInfoEntity.PhotosEntity> = _photosLiveData
    //region Mappers
    private val photosMapper by lazy { digMapper(ArtistPhotosPMapper::class) }
    //endregion

    fun runTaskFetchArtistPhoto(artistName: String) = launchBehind {
        _photosLiveData reqData {
            fetchArtistPhotoCase.execMapping(photosMapper,
                                             FetchArtistPhotoReq(ArtistParams(artistName = artistName)))
        }
    }
}
