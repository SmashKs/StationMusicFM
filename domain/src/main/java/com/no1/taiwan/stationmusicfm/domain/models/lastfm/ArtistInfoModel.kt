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
import java.awt.Image

data class ArtistInfoModel(
    val artist: ArtistModel?
) : Model {
    data class ArtistModel(
        val name: String? = DEFAULT_STR,
        val mbid: String? = DEFAULT_STR,
        val match: String? = DEFAULT_STR,
        val url: String? = DEFAULT_STR,
        val images: List<Image>? = emptyList(),
        val streamable: String? = DEFAULT_STR,
        val listeners: String? = DEFAULT_STR,
        val onTour: String? = DEFAULT_STR,
        val playCount: String? = DEFAULT_STR,
        val stats: StatsModel? = null,
        val similar: SimilarModel? = null,
        val tags: CommonLastFmModel.TagsModel? = null,
        val bio: BioModel? = null
    ) : Model

    data class BioModel(
        val links: LinksModel?,
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Model

    data class LinksModel(
        val link: LinkModel?
    ) : Model

    data class LinkModel(
        val text: String?,
        val rel: String?,
        val href: String?
    ) : Model

    data class StatsModel(
        val listeners: String?,
        val playCount: String?
    ) : Model

    data class SimilarModel(
        val artists: List<ArtistModel>?
    ) : Model
}
