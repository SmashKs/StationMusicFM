package com.no1.taiwan.stationmusicfm.data.repositories

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.data.data.DataMapper
import com.no1.taiwan.stationmusicfm.data.data.DataMapperPool

abstract class BaseRepository(protected val mapperPool: DataMapperPool) {
    /**
     * Get a mapper object from the mapper pool.
     */
    protected inline fun <reified DM : DataMapper> digDataMapper() = cast<DM>(mapperPool[DM::class.java])
}
