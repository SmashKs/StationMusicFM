package com.no1.taiwan.stationmusicfm.data.remote

import com.no1.taiwan.stationmusicfm.data.remote.config.LastFmConfig

/**
 * Factory that creates different implementations of [com.no1.taiwan.stationmusicfm.data.remote.config.ApiConfig].
 */
class RestfulApiFactory {
    fun createLastFmConfig() = LastFmConfig()
}
