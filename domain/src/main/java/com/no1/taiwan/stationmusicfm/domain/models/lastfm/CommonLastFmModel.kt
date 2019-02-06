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

package com.no1.taiwan.stationmusicfm.domain.models.lastfm

import com.no1.taiwan.stationmusicfm.domain.models.Model
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

object CommonLastFmModel {
    data class TagsModel(
        val tags: List<TagInfoModel.TagModel> = emptyList(),
        val attr: AttrModel = AttrModel()
    ) : Model

    data class ImageModel(
        val text: String = DEFAULT_STR,
        val size: String = DEFAULT_STR
    ) : Model

    data class StreamableModel(
        val text: String = DEFAULT_STR,
        val fulltrack: String = DEFAULT_STR
    ) : Model

    data class WikiModel(
        val published: String = DEFAULT_STR,
        val summary: String = DEFAULT_STR,
        val content: String = DEFAULT_STR
    ) : Model

    data class AttrModel(
        val artist: String = DEFAULT_STR,
        val totalPages: String = DEFAULT_STR,
        val total: String = DEFAULT_STR,
        val rank: String = DEFAULT_STR,
        val position: String = DEFAULT_STR,
        val perPage: String = DEFAULT_STR,
        val page: String = DEFAULT_STR
    ) : Model
}
