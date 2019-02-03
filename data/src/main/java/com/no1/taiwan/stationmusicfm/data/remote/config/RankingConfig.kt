package com.no1.taiwan.stationmusicfm.data.remote.config

import com.no1.taiwan.stationmusicfm.data.BuildConfig

/**
 * The configuration of a remote google news api service.
 */
class RankingConfig : ApiConfig {
    companion object {
        const val API_REQUEST = BuildConfig.RankingUriRequest
        // All basic http api url of Search Music.
        private const val BASE_URL = BuildConfig.RankingUriDomain
    }

    override val apiBaseUrl = BASE_URL
}
