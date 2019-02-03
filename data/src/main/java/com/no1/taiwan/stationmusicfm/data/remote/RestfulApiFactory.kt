package com.no1.taiwan.stationmusicfm.data.remote

import com.no1.taiwan.stationmusicfm.data.remote.config.LastFmConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.MusicConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.MusicSeekerConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.RankingConfig

/**
 * Factory that creates different implementations of [com.no1.taiwan.stationmusicfm.data.remote.config.ApiConfig].
 */
class RestfulApiFactory {
    fun createLastFmConfig() = LastFmConfig()
    fun createMusicConfig() = MusicConfig()
    fun createMusicSeekerConfig() = MusicSeekerConfig()
    fun createRankingConfig() = RankingConfig()
}
