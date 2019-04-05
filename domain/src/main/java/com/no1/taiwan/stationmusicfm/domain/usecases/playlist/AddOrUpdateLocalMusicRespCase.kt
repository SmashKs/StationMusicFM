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

package com.no1.taiwan.stationmusicfm.domain.usecases.playlist

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_ADD_OR_MINUS
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_LIST
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams
import com.no1.taiwan.stationmusicfm.domain.repositories.PlaylistRepository
import com.no1.taiwan.stationmusicfm.domain.usecases.AddOrUpdateLocalMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddOrUpdateLocalMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase.RequestValues

class AddOrUpdateLocalMusicRespCase(
    private val repository: PlaylistRepository
) : AddOrUpdateLocalMusicCase() {
    /** Provide a common parameter variable for the children class. */
    override var requestValues: AddOrUpdateLocalMusicReq? = null

    override suspend fun acquireCase() = attachParameter {
        val addRes = repository.addOrUpdateLocalMusic(it.parameters)
        // If add false, just return. It's unnecessary to increase the track count.
        if (!addRes) return@attachParameter addRes
        // Get the parameter and pass into `increasing` or `deceasing` track count operation.
        val params = it.parameters.toApiAnyParam()
        val addOrMinus = cast<Boolean>(params[PARAM_NAME_PLAYLIST_ADD_OR_MINUS])
        // Playlist list will be a string with multiple number so it needs to be separated by ",".
        cast<String>(params[PARAM_NAME_PLAYLIST_LIST])
            .takeIf(String::isNotBlank)
            ?.split(",")
            ?.forEach { repository.updateCountOfPlaylist(PlaylistParams(listOf(it.toInt()), addOrMinus = addOrMinus)) }
        true
    }

    class Request(val parameters: Parameterable = LocalMusicParams()) : RequestValues
}
