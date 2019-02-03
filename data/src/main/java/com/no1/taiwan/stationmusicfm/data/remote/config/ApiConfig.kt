package com.no1.taiwan.stationmusicfm.data.remote.config

/**
 * Interface of the setting of the difference http configurations.
 */
interface ApiConfig {
    /**
     * Obtain the base http url.
     *
     * @return restful api base url information.
     */
    val apiBaseUrl: String
}
