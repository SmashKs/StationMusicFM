/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.no1.taiwan.stationmusicfm.entities.mappers.lastfm

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.entities.TracksPreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity

/**
 * A transforming mapping between [TrackInfoModel.TracksModel] and [TrackInfoEntity.TracksEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class TracksPMapper(
    private val trackMapper: TrackPMapper,
    private val attrMapper: AttrPMapper
) : TracksPreziMap {
    override fun toEntityFrom(model: TrackInfoModel.TracksModel) = model.run {
        TrackInfoEntity.TracksEntity(tracks.map(trackMapper::toEntityFrom),
                                     attr.let(attrMapper::toEntityFrom))
    }

    override fun toModelFrom(entity: TrackInfoEntity.TracksEntity) = entity.run {
        TrackInfoModel.TracksModel(tracks.map(trackMapper::toModelFrom),
                                   attrMapper.toModelFrom(attr))
    }
}
