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

import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable

/**
 * This interface will be the similar to [com.no1.taiwan.newsbasket.data.datastores.DataStore].
 * Using prefix name (fetch), (add), (update), (delete), (keep)
 */
interface MusicBankRepository {
    //region Music Rank
    suspend fun fetchMusicRanking(parameters: Parameterable): MusicInfoModel.MusicModel

    suspend fun fetchMusic(parameters: Parameterable): MusicInfoModel.MusicModel

    suspend fun fetchHotPlaylist(parameters: Parameterable): HotPlaylistModel.HotListModel

    suspend fun fetchSongList(parameters: Parameterable): CommonMusicModel.PlayListModel
    //endregion
}
