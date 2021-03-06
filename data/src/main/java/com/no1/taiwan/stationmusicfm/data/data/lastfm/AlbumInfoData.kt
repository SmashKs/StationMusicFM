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

import com.devrapid.kotlinshaver.trimMarginAndNewLine
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT

data class AlbumInfoData(
    val album: AlbumData?
) : Data {
    data class AlbumData(
        val artist: String?,
        @SerializedName("playcount")
        val playCount: String? = null
    ) : BaseAlbumData(), Data {
        override fun toString() = """
            |${this::class.java.simpleName}(
            |artist=$artist
            |playCount=$playCount
            |${super.toString()})
            |""".trimMarginAndNewLine()
    }

    data class AlbumWithArtistData(
        val artist: ArtistInfoData.ArtistData?,
        @SerializedName("playcount")
        val playCount: String? = null,
        val index: Int = DEFAULT_INT
    ) : BaseAlbumData(), Data {
        override fun toString() = """
            |${this::class.java.simpleName}(
            |artist=$artist
            |playCount=$playCount
            |index=$index
            |${super.toString()})
            |""".trimMarginAndNewLine()
    }

    open class BaseAlbumData(
        @SerializedName("@attr")
        var attr: CommonLastFmData.AttrData? = null,
        @SerializedName("image")
        var images: List<CommonLastFmData.ImageData>? = null,
        var listeners: String? = null,
        var mbid: String? = null,
        var name: String? = null,
        var tags: CommonLastFmData.TagsData? = null,
        var title: String? = null,
        @SerializedName("tracks")
        var tracks: TopTrackInfoData.TracksData? = null,
        var url: String? = null,
        var wiki: CommonLastFmData.WikiData? = null
    ) : Data {
        override fun toString() = """
            |attr=$attr
            |images=$images
            |listeners=$listeners
            |mbid=$mbid
            |name=$name
            |tags=$tags
            |title=$title
            |tracks=$tracks
            |url=$url
            |wiki=$wiki
            |""".trimMarginAndNewLine()
    }
}
