package com.no1.taiwan.stationmusicfm.data.remote.config

import com.no1.taiwan.stationmusicfm.data.BuildConfig

/**
 * The configuration of a remote google news api service.
 */
class MusicConfig : ApiConfig {
    companion object {
        const val API_REQUEST = BuildConfig.SongUriRequest
        // All basic http api url of Search Music.
        private const val BASE_URL = BuildConfig.SongUriDomain
    }

    override val apiBaseUrl = BASE_URL
}
