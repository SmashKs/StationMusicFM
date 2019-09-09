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

package com.no1.taiwan.stationmusicfm.utils.aac.delegates

import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.domain.usecases.AddOrUpdateLocalMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddOrUpdateLocalMusicReq
import com.no1.taiwan.stationmusicfm.entities.mappers.MusicToParamsMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MakeLocalMusic(
    private val addLocalMusicCase: AddOrUpdateLocalMusicCase
) : LocalMusicDelegate {
    override fun runTaskAddOrUpdateToPlayHistory(
        song: CommonMusicEntity.SongEntity,
        playlistIndex: Int,
        addOrMinus: Boolean
    ) = CoroutineScope(Dispatchers.IO).async {
        execAddOrUpdateToPlayHistory(playlistIndex) {
            MusicToParamsMapper().toParamsWith(song,
                                               it,
                                               addOrMinus)
        }
    }

    override fun runTaskUpdateToPlayHistory(
        song: LocalMusicEntity,
        playlistIndex: Int,
        addOrMinus: Boolean
    ) =
        CoroutineScope(Dispatchers.IO).async {
            execAddOrUpdateToPlayHistory(playlistIndex) {
                MusicToParamsMapper().toParamsWith(song,
                                                   it,
                                                   addOrMinus)
            }
        }

    override fun runTaskAddDownloadedTrackInfo(
        song: CommonMusicEntity.SongEntity,
        localUri: String
    ) =
        CoroutineScope(Dispatchers.IO).async {
            val parameter = MusicToParamsMapper()
                .toParamsWith(song, listOf(PlaylistIndex.DOWNLOADED.ordinal))
                .copy(hasOwn = true, localTrackUri = localUri)
            addLocalMusicCase.exec(AddOrUpdateLocalMusicReq(parameter))
        }

    private suspend fun execAddOrUpdateToPlayHistory(
        playlistIndex: Int,
        transformBlock: (playlistId: List<Int>) -> LocalMusicParams
    ): Boolean {
        val playlistId = playlistIndex
            .takeIf { it != DEFAULT_INT }
            ?.let { listOf(it) }
            .orEmpty()
        val parameters = transformBlock(playlistId)
        return addLocalMusicCase.exec(AddOrUpdateLocalMusicReq(parameters))
    }
}
