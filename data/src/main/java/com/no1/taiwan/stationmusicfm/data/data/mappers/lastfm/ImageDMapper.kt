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

package com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm

import com.no1.taiwan.stationmusicfm.data.data.ImageDataMap
import com.no1.taiwan.stationmusicfm.data.data.lastfm.CommonLastFmData
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel

/**
 * A transforming mapping between [CommonLastFmData.ImageData] and [CommonLastFmModel.ImageModel].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class ImageDMapper : ImageDataMap {
    override fun toModelFrom(data: CommonLastFmData.ImageData) = data.run {
        CommonLastFmModel.ImageModel(text.orEmpty(), size.orEmpty())
    }

    override fun toDataFrom(model: CommonLastFmModel.ImageModel) = model.run {
        CommonLastFmData.ImageData(text, size)
    }
}
