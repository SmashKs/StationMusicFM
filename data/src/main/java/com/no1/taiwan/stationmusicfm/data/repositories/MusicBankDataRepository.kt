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

import com.no1.taiwan.stationmusicfm.data.data.DataMapperPool
import com.no1.taiwan.stationmusicfm.data.data.HotListDataMap
import com.no1.taiwan.stationmusicfm.data.data.MusicDataMap
import com.no1.taiwan.stationmusicfm.data.data.PlaylistDataMap
import com.no1.taiwan.stationmusicfm.data.data.RankChartDataMap
import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.repositories.MusicBankRepository

/**
 * The data repository for being responsible for selecting an appropriate data store to access
 * the data.
 * Also we need to do one time for getting the data then transform and wrap to Domain layer by
 * [Mapper].
 *
 * @property local from database/file/memory data store.
 * @property mapperPool keeping all of the data mapper here.
 */
class MusicBankDataRepository constructor(
    private val local: DataStore,
    private val remote: DataStore,
    mapperPool: DataMapperPool
) : BaseRepository(mapperPool), MusicBankRepository {
    private val musicMapper by lazy { digDataMapper<MusicDataMap>() }
    private val hotListMapper by lazy { digDataMapper<HotListDataMap>() }
    private val playlistMapper by lazy { digDataMapper<PlaylistDataMap>() }
    private val rankChartMapper by lazy { digDataMapper<RankChartDataMap>() }

    override suspend fun fetchMusicRanking(parameters: Parameterable) =
        remote.getMusicRanking(parameters).run(rankChartMapper::toModelFrom)

    override suspend fun fetchMusic(parameters: Parameterable) =
        remote.getMusic(parameters).data.run(musicMapper::toModelFrom)

    override suspend fun fetchHotPlaylist(parameters: Parameterable) =
        remote.getHotPlaylist(parameters).data.run(hotListMapper::toModelFrom)

    override suspend fun fetchPlaylistDetail(parameters: Parameterable) =
        remote.getPlaylistDetail(parameters).data.run(playlistMapper::toModelFrom)
}
