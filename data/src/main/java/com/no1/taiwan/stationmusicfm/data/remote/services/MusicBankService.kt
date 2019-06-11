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

package com.no1.taiwan.stationmusicfm.data.remote.services

import com.no1.taiwan.stationmusicfm.data.data.musicbank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.SongListInfoData
import com.no1.taiwan.stationmusicfm.data.remote.config.RankingConfig
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Thru [retrofit2.Retrofit] we can just define the interfaces which we want to access for.
 * Using prefix name (retrieve), (insert), (replace), (release)
 */
interface MusicBankService {
    @GET("${RankingConfig.API_REQUEST}/rank_song_list")
    suspend fun retrieveMusicRanking(@QueryMap queries: Map<String, String>): MusicInfoData

    @GET("${RankingConfig.API_REQUEST}/search")
    @Deprecated("Seeker Bank is better for searching more sources.")
    suspend fun retrieveSearchMusic(@QueryMap queries: Map<String, String>): MusicInfoData

    @GET("${RankingConfig.API_REQUEST}/hot_song_list")
    suspend fun retrieveHotPlaylist(@QueryMap queries: Map<String, String>): HotPlaylistData

    @GET("${RankingConfig.API_REQUEST}/song_list")
    suspend fun retrieveSongList(@QueryMap queries: Map<String, String>): SongListInfoData
}
