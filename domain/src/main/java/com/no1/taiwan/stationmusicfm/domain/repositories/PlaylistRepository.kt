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

package com.no1.taiwan.stationmusicfm.domain.repositories

import com.no1.taiwan.stationmusicfm.domain.models.playlist.LocalMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.playlist.PlaylistInfoModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable

/**
 * This interface will be the similar to [com.no1.taiwan.stationmusicfm.data.datastores.DataStore].
 * Using prefix name (fetch), (add), (update), (delete), (keep)
 */
interface PlaylistRepository {
    suspend fun fetchLocalMusics(parameters: Parameterable): List<LocalMusicModel>

    suspend fun addLocalMusic(parameters: Parameterable): Boolean

    suspend fun deleteLocalMusic(parameters: Parameterable): Boolean

    suspend fun fetchPlaylists(parameters: Parameterable): List<PlaylistInfoModel>

    suspend fun fetchPlaylist(parameters: Parameterable): PlaylistInfoModel

    suspend fun addPlaylist(parameters: Parameterable): Boolean

    suspend fun updatePlaylist(parameters: Parameterable): Boolean

    suspend fun updateCountOfPlaylist(parameters: Parameterable): Boolean

    suspend fun deletePlaylist(parameters: Parameterable): Boolean

    suspend fun fetchListenedHistories(parameters: Parameterable): List<LocalMusicModel>

    suspend fun fetchTypeOfHistories(parameters: Parameterable): List<LocalMusicModel>
}
