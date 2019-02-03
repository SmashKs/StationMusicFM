package com.no1.taiwan.stationmusicfm.entities

import com.no1.taiwan.stationmusicfm.entities.mappers.Mapper

typealias PresentationMapper = Mapper<*, *>
typealias PresentationMapperPool = Map<Class<out PresentationMapper>, PresentationMapper>
