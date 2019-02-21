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
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.AlbumParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchAlbumReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AlbumPMapper
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExploreAlbumViewModel(
    private val fetchAlbumCase: FetchAlbumCase,
    private val mapperPool: PreziMapperPool
) : AutoViewModel() {
    private val _albumLiveData by lazy { RespMutableLiveData<AlbumInfoEntity.AlbumEntity>() }
    val albumLiveData: RespLiveData<AlbumInfoEntity.AlbumEntity> = _albumLiveData
    private val albumMapper by lazy { cast<AlbumPMapper>(mapperPool[AlbumPMapper::class.java]) }

    fun runTaskFetchAlbum(mbid: String, albumName: String, artistName: String) = GlobalScope.launch {
        _albumLiveData reqData {
            fetchAlbumCase.execMapping(albumMapper, FetchAlbumReq(AlbumParams(mbid, albumName, artistName)))
        }
    }
}
