package com.no1.taiwan.stationmusicfm.data.data

import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper

typealias DataMapper = Mapper<*, *>
typealias DataMapperPool = Map<Class<out DataMapper>, DataMapper>
