package com.no1.taiwan.stationmusicfm.data.remote.config

/**
 * The configuration of a remote google news api service.
 */
class LastFmConfig : ApiConfig {
    companion object {
        const val API_REQUEST = "/2.0"
        // All basic http api url of Search Music.
        private const val BASE_URL = "http://ws.audioscrobbler.com"
    }

    override val apiBaseUrl = BASE_URL
}
