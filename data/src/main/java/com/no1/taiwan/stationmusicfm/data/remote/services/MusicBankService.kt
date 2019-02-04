package com.no1.taiwan.stationmusicfm.data.remote.services

import com.no1.taiwan.stationmusicfm.data.data.rank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.rank.MusicData
import com.no1.taiwan.stationmusicfm.data.data.rank.PlaylistInfoData
import com.no1.taiwan.stationmusicfm.data.data.rank.RankChartData
import com.no1.taiwan.stationmusicfm.data.remote.config.RankingConfig
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Thru [retrofit2.Retrofit] we can just define the interfaces which we want to access for.
 * Using prefix name (retrieve), (insert), (replace), (release)
 */
interface MusicBankService {
    @GET("${RankingConfig.API_REQUEST}/rank_song_list")
    fun retrieveMusicRanking(@QueryMap queries: Map<String, String>): Deferred<RankChartData>

    @GET("${RankingConfig.API_REQUEST}/search")
    fun retrieveSearchMusic(@QueryMap queries: Map<String, String>): Deferred<MusicData>

    @GET("${RankingConfig.API_REQUEST}/hot_song_list")
    fun retrieveHotPlaylist(@QueryMap queries: Map<String, String>): Deferred<HotPlaylistData>

    @GET("${RankingConfig.API_REQUEST}/song_list")
    fun retrievePlaylistDetail(@QueryMap queries: Map<String, String>): Deferred<PlaylistInfoData>
}
