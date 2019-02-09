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
import com.no1.taiwan.stationmusicfm.entities.TagsPreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity

/**
 * A transforming mapping between [CommonLastFmModel.TagsModel] and [TagInfoEntity.TagsEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class TagsPMapper(
    private val tagMapper: TagPMapper,
    private val attrMapper: AttrPMapper
) : TagsPreziMap {
    override fun toEntityFrom(model: CommonLastFmModel.TagsModel) = model.run {
        TagInfoEntity.TagsEntity(tags.map(tagMapper::toEntityFrom),
                                 attr.let(attrMapper::toEntityFrom))
    }

    override fun toModelFrom(entity: TagInfoEntity.TagsEntity) = entity.run {
        CommonLastFmModel.TagsModel(tags.map(tagMapper::toModelFrom), attrMapper.toModelFrom(attr))
    }
}