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
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.domain.models.rank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.PlaylistInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.RankChartModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.repositories.MusicBankRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * The data repository for being responsible for selecting an appropriate data store to access
 * the data.
 * Also we need to do [async] & [await] one time for getting the data then transform and wrap to Domain layer.
 *
 * @property local from database/file/memory data store.
 * @property mapperPool keeping all of the data mapper here.
 */
class MusicBankDataRepository constructor(
    private val local: DataStore,
    private val remote: DataStore,
    mapperPool: DataMapperPool
) : BaseRepository(mapperPool), MusicBankRepository {
    override fun fetchMusicRanking(parameterable: Parameterable): Deferred<RankChartModel> {
        remote.getMusicRanking()
    }

    override fun fetchSearchMusic(parameterable: Parameterable): Deferred<MusicInfoModel> {
        remote.getSearchMusic()
    }

    override fun fetchHotPlaylist(parameterable: Parameterable): Deferred<HotPlaylistModel> {
        remote.getHotPlaylist()
    }

    override fun fetchPlaylistDetail(parameterable: Parameterable): Deferred<PlaylistInfoModel> {
        remote.getPlaylistDetail()
    }

}
