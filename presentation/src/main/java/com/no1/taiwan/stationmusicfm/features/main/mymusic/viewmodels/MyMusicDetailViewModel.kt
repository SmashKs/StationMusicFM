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
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTypeOfHistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTypeOfHistsReq
import com.no1.taiwan.stationmusicfm.entities.mappers.playlist.LocalMusicPMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.features.LocalMusics
import com.no1.taiwan.stationmusicfm.ktx.aac.livedata.SilentLiveData
import com.no1.taiwan.stationmusicfm.ktx.aac.livedata.SilentMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.LocalMusicDelegate
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.MakeLocalMusic
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.execListMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class MyMusicDetailViewModel(
    private val fetchTypeOfHistsCase: FetchTypeOfHistsCase,
    private val makeLocalMusic: MakeLocalMusic,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate, LocalMusicDelegate {
    private val _playlist by lazy { RespMutableLiveData<LocalMusics>() }
    val playlist: RespLiveData<LocalMusics> = _playlist
    private val _removeRes by lazy { SilentMutableLiveData<Boolean>() }
    val removeRes: SilentLiveData<Boolean> = _removeRes
    //region Mappers
    private val playlistMapper by lazy { digMapper(LocalMusicPMapper::class) }
    //endregion

    override fun runTaskAddOrUpdateToPlayHistory(song: SongEntity, playlistIndex: Int, addOrMinus: Boolean) =
        launchBehind { makeLocalMusic.runTaskAddOrUpdateToPlayHistory(song, playlistIndex, addOrMinus).await() }

    override fun runTaskUpdateToPlayHistory(song: LocalMusicEntity, playlistIndex: Int, addOrMinus: Boolean) =
        launchBehind {
            val result = makeLocalMusic.runTaskUpdateToPlayHistory(song, playlistIndex, addOrMinus).await()
            _removeRes.postValue(result)
        }

    override fun runTaskAddDownloadedTrackInfo(song: SongEntity, localUri: String) =
        launchBehind { makeLocalMusic.runTaskAddDownloadedTrackInfo(song, localUri).await() }

    fun runTaskFetchPlaylist(id: Int) = launchBehind {
        _playlist reqData {
            fetchTypeOfHistsCase.execListMapping(playlistMapper, FetchTypeOfHistsReq(PlaylistParams(listOf(id))))
        }
    }

    fun executeRemoveLiveDataMusic(entity: LocalMusicEntity) = launchBehind {
        val value = _playlist.value as? ResponseState.Success ?: return@launchBehind
        val data = value.data?.filter { it.id != entity.id }.orEmpty()
        _playlist.postValue(ResponseState.Success(data))
    }
}
