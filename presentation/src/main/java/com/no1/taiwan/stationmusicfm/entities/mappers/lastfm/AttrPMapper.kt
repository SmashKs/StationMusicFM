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

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.entities.AttrPreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.CommonLastFmEntity

/**
 * A transforming mapping between [CommonLastFmModel.AttrModel] and [CommonLastFmEntity.AttrEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class AttrPMapper : AttrPreziMap {
    override fun toEntityFrom(model: CommonLastFmModel.AttrModel) = model.run {
        CommonLastFmEntity.AttrEntity(artist, totalPages, total, rank, position, perPage, page)
    }

    override fun toModelFrom(entity: CommonLastFmEntity.AttrEntity) = entity.run {
        CommonLastFmModel.AttrModel(artist, totalPages, total, rank, position, perPage, page)
    }
}
