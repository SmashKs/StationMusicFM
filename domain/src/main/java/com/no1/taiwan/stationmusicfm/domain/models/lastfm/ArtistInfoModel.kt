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

object ArtistInfoModel {
    data class ArtistModel(
        val name: String = DEFAULT_STR,
        val mbid: String = DEFAULT_STR,
        val match: String = DEFAULT_STR,
        val url: String = DEFAULT_STR,
        val images: List<CommonLastFmModel.ImageModel> = emptyList(),
        val streamable: String = DEFAULT_STR,
        val listeners: String = DEFAULT_STR,
        val onTour: String = DEFAULT_STR,
        val playCount: String = DEFAULT_STR,
        val stats: StatsModel = StatsModel(),
        val similars: List<ArtistModel> = emptyList(),
        val tags: List<TagInfoModel.TagModel> = emptyList(),
        val bio: BioModel = BioModel()
    ) : Model

    data class ArtistsModel(
        val artists: List<ArtistInfoModel.ArtistModel> = emptyList(),
        val attr: CommonLastFmModel.AttrModel = CommonLastFmModel.AttrModel()
    ) : Model

    data class ArtistPhotoModel(
        val url: String = DEFAULT_STR,
        val hashCode: String = DEFAULT_STR
    ) : Model

    data class ArtistPhotosModel(
        val photos: List<ArtistPhotoModel> = emptyList(),
        val hasNext: Boolean = false
    ) : Model

    data class BioModel(
        val link: LinkModel = LinkModel(),
        val published: String = DEFAULT_STR,
        val summary: String = DEFAULT_STR,
        val content: String = DEFAULT_STR
    ) : Model

    data class LinkModel(
        val text: String = DEFAULT_STR,
        val rel: String = DEFAULT_STR,
        val href: String = DEFAULT_STR
    ) : Model

    data class StatsModel(
        val listeners: String = DEFAULT_STR,
        val playCount: String = DEFAULT_STR
    ) : Model
}
