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

package com.no1.taiwan.stationmusicfm.data.datastores

import com.no1.taiwan.stationmusicfm.data.data.rank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.rank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.rank.PlaylistInfoData
import com.no1.taiwan.stationmusicfm.data.data.rank.RankChartData
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService
import com.no1.taiwan.stationmusicfm.data.remote.services.MusicBankService
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import kotlinx.coroutines.Deferred

/**
 * The implementation of the remote data store. The responsibility is selecting a correct
 * remote service to access the data.
 */
class RemoteDataStore(
    private val lastFmService: LastFmService,
    private val musicBankService: MusicBankService
) : DataStore {
    override fun getMusicRanking(parameterable: Parameterable): Deferred<RankChartData> {
        return musicBankService.retrieveMusicRanking(parameterable.toApiParam())
    }

    override fun getSearchMusic(parameterable: Parameterable): Deferred<MusicInfoData> {
        return musicBankService.retrieveSearchMusic(parameterable.toApiParam())
    }

    override fun getHotPlaylist(parameterable: Parameterable): Deferred<HotPlaylistData> {
        return musicBankService.retrieveHotPlaylist(parameterable.toApiParam())
    }

    override fun getPlaylistDetail(parameterable: Parameterable): Deferred<PlaylistInfoData> {
        return musicBankService.retrievePlaylistDetail(parameterable.toApiParam())
    }
}
