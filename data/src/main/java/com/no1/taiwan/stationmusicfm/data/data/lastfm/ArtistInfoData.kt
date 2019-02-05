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

package com.no1.taiwan.stationmusicfm.data.data.lastfm

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class ArtistInfoData(
    val artist: ArtistData?
) : Data {
    data class ArtistData(
        val name: String? = DEFAULT_STR,
        val mbid: String? = DEFAULT_STR,
        val match: String? = DEFAULT_STR,
        val url: String? = DEFAULT_STR,
        @SerializedName("image")
        val images: List<Image>? = emptyList(),
        val streamable: String? = DEFAULT_STR,
        val listeners: String? = DEFAULT_STR,
        @SerializedName("ontour")
        val onTour: String? = DEFAULT_STR,
        @SerializedName("playcount")
        val playCount: String? = DEFAULT_STR,
        val stats: StatsData? = null,
        val similar: SimilarData? = null,
        val tags: CommonLastFmData.TagsData? = null,
        val bio: BioData? = null
    ) : Data

    data class BioData(
        val links: LinksData?,
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Data

    data class LinksData(
        val link: LinkData?
    ) : Data

    data class LinkData(
        @SerializedName("#text")
        val text: String?,
        val rel: String?,
        val href: String?
    ) : Data

    data class StatsData(
        val listeners: String?,
        @SerializedName("playcount")
        val playCount: String?
    ) : Data

    data class SimilarData(
        @SerializedName("artist")
        val artists: List<ArtistData>?
    ) : Data
}
