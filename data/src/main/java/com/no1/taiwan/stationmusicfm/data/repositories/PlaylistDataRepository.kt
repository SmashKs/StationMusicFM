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

package com.no1.taiwan.stationmusicfm.data.repositories

import com.no1.taiwan.stationmusicfm.data.data.mappers.playlist.LocalMusicDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.playlist.PlaylistInfoDMapper
import com.no1.taiwan.stationmusicfm.data.delegates.DataMapperDigger
import com.no1.taiwan.stationmusicfm.data.stores.DataStore
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.repositories.PlaylistRepository

/**
 * The data repository for being responsible for selecting an appropriate data store to access
 * the data.
 * Also we need to do one time for getting the data then transform and wrap to Domain layer by
 * [Mapper].
 *
 * @property local from database/file/memory data store.
 */
class PlaylistDataRepository constructor(
    private val local: DataStore,
    diggerDelegate: DataMapperDigger
) : PlaylistRepository, DataMapperDigger by diggerDelegate {
    private val musicMapper by lazy { digMapper(LocalMusicDMapper::class) }
    private val playlistMapper by lazy { digMapper(PlaylistInfoDMapper::class) }

    override suspend fun fetchLocalMusics(parameters: Parameterable) =
        local.fetchLocalMusics(parameters).map(musicMapper::toModelFrom)

    override suspend fun addLocalMusic(parameters: Parameterable) = local.addLocalMusic(parameters)

    override suspend fun deleteLocalMusic(parameters: Parameterable) = local.deleteLocalMusic(parameters)

    override suspend fun fetchPlaylists(parameters: Parameterable) =
        local.fetchPlaylists().map(playlistMapper::toModelFrom)

    override suspend fun fetchPlaylist(parameters: Parameterable) =
        local.fetchPlaylist(parameters).run(playlistMapper::toModelFrom)

    override suspend fun addPlaylist(parameters: Parameterable) = local.addPlaylist(parameters)

    override suspend fun updatePlaylist(parameters: Parameterable) = local.updatePlaylist(parameters)

    override suspend fun updateCountOfPlaylist(parameters: Parameterable) = local.updateCountOfPlaylist(parameters)

    override suspend fun deletePlaylist(parameters: Parameterable) = local.deletePlaylist(parameters)

    override suspend fun fetchListenedHistories(parameters: Parameterable) =
        local.fetchListenedHistories(parameters).map(musicMapper::toModelFrom)

    override suspend fun fetchTypeOfHistories(parameters: Parameterable) =
        local.fetchTypeOfHistories(parameters).map(musicMapper::toModelFrom)
}
