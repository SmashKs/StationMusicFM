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

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

object CommonLastFmData {
    data class TopAlbumsData(
        @SerializedName("album")
        val albums: List<AlbumInfoData.AlbumWithArtistData>,
        @SerializedName("@attr")
        val attr: CommonLastFmData.AttrData?
    ) : Data

    data class TagsData(
        @SerializedName("tag")
        val tags: List<TagInfoData.TagData>?,
        @SerializedName("@attr")
        val attr: AttrData?
    ) : Data

    data class ImageData(
        @SerializedName("#text")
        val text: String?,
        val size: String?
    ) : Data

    data class StreamableData(
        @SerializedName("#text")
        val text: String?,
        val fulltrack: String?
    ) : Data

    data class WikiData(
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Data

    data class AttrData(
        val artist: String?,
        val totalPages: String?,
        val total: String?,
        val rank: String?,
        val position: String?,
        val perPage: String?,
        val page: String?
    ) : Data
}
