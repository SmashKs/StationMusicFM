package com.no1.taiwan.stationmusicfm.data.datastores

import com.tencent.mmkv.MMKV

/**
 * The implementation of the local data store. The responsibility is selecting a correct
 * local service(Database/Local file) to access the data.
 */
class LocalDataStore(
    private val mmkv: MMKV
) : DataStore
