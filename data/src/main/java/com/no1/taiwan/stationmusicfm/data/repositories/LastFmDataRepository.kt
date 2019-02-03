package com.no1.taiwan.stationmusicfm.data.repositories

import com.no1.taiwan.stationmusicfm.data.data.DataMapperPool
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.domain.repositories.LastFmRepository
import kotlinx.coroutines.async

/**
 * The data repository for being responsible for selecting an appropriate data store to access
 * the data.
 * Also we need to do [async] & [await] one time for getting the data then transform and wrap to Domain layer.
 *
 * @property cache cache data store.
 * @property local from database/file/memory data store.
 * @property mapperPool keeping all of the data mapper here.
 */
class LastFmDataRepository constructor(
    private val local: DataStore,
    private val remote: DataStore,
    mapperPool: DataMapperPool
) : BaseRepository(mapperPool), LastFmRepository
