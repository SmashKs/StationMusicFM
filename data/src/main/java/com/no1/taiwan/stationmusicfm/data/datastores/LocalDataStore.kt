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

import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.data.local.services.RankingDao
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.tencent.mmkv.MMKV

/**
 * The implementation of the local data store. The responsibility is selecting a correct
 * local service(Database/Local file) to access the data.
 */
class LocalDataStore(
    private val rankingDao: RankingDao,
    private val mmkv: MMKV
) : DataStore {
    override suspend fun createRankingData(params: List<RankingIdData>): Boolean {
        rankingDao.insert(*params.toTypedArray())
        return true
    }

    override suspend fun getRankingData() = rankingDao.getRankings()

    //region UnsupportedOperationException
    override suspend fun getMusicRanking(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getMusic(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getHotPlaylist(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getSongList(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getAlbumInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getArtistInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getArtistTopAlbum(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getArtistTopTrack(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getSimilarArtistInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getArtistPhotosInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getTrackInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getSimilarTrackInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getChartTopTrack(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getChartTopArtist(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getChartTopTag(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getTagInfo(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getTagTopAlbum(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getTagTopArtist(parameterable: Parameterable) = throw UnsupportedOperationException()

    override suspend fun getTagTopTrack(parameterable: Parameterable) = throw UnsupportedOperationException()
    //endregion
}
