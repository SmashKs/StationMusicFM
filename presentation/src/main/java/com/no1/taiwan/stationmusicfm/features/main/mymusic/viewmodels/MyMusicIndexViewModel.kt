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

package com.no1.taiwan.stationmusicfm.features.main.mymusic.viewmodels

import com.no1.taiwan.stationmusicfm.domain.ResponseState
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTypeOfHistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTypeOfHistsReq
import com.no1.taiwan.stationmusicfm.entities.mappers.playlist.LocalMusicPMapper
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.ktx.acc.livedata.TransformedLiveData
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execListMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class MyMusicIndexViewModel(
    private val fetchTypeOfHistsCase: FetchTypeOfHistsCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _playlist by lazy { RespMutableLiveData<List<LocalMusicEntity>>() }
    val playlist: RespLiveData<List<LocalMusicEntity>> = _playlist
    val favorites = FavoriteLiveData(_playlist)
    val downloads = DownloadLiveData(_playlist)
    private val playlistMapper by lazy { digMapper(LocalMusicPMapper::class) }
    private lateinit var tempIds: List<Int>

    fun runTaskFetchPlaylist(ids: List<Int>) = launchBehind {
        tempIds = ids
        _playlist reqData {
            fetchTypeOfHistsCase.execListMapping(playlistMapper, FetchTypeOfHistsReq(PlaylistParams(ids)))
        }
    }

    inner class FavoriteLiveData(
        override val source: RespMutableLiveData<List<LocalMusicEntity>>
    ) : TransformedLiveData<ResponseState<List<LocalMusicEntity>>, List<LocalMusicEntity>>() {
        override fun getTransformed(source: ResponseState<List<LocalMusicEntity>>) =
            if (tempIds.contains(PlaylistIndex.Favorite.ordinal) && source is ResponseState.Success<List<LocalMusicEntity>>)
                source.data?.filter { PlaylistIndex.Favorite.ordinal.toString() in it.playlistList }.orEmpty()
            else
                emptyList()
    }

    inner class DownloadLiveData(
        override val source: RespMutableLiveData<List<LocalMusicEntity>>
    ) : TransformedLiveData<ResponseState<List<LocalMusicEntity>>, List<LocalMusicEntity>>() {
        override fun getTransformed(source: ResponseState<List<LocalMusicEntity>>) =
            if (tempIds.contains(PlaylistIndex.Downloaded.ordinal) && source is ResponseState.Success<List<LocalMusicEntity>>)
                source.data?.filter { it.localTrackUri.isNotBlank() }.orEmpty()
            else
                emptyList()
    }
}
