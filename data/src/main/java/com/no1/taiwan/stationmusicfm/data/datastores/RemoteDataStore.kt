package com.no1.taiwan.stationmusicfm.data.datastores

import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService

/**
 * The implementation of the remote data store. The responsibility is selecting a correct
 * remote service to access the data.
 */
class RemoteDataStore(
    private val lastFmService: LastFmService
) : DataStore
