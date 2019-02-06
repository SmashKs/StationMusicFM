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

data class TrackInfoData(
    val track: TrackData?
) : Data {
    data class TrackData(
        val streamable: CommonLastFmData.StreamableData?
    ) : BaseTrackData(), Data

    data class TrackWithStreamableStringData(
        val streamable: String?
    ) : BaseTrackData(), Data

    open class BaseTrackData(
        var album: AlbumInfoData.AlbumData? = null,
        @SerializedName("@attr")
        var attr: CommonLastFmData.AttrData? = null,
        var artist: ArtistInfoData.ArtistData? = null,
        var duration: String? = null,
        @SerializedName("image")
        var images: List<CommonLastFmData.ImageData>? = null,
        var listeners: String? = null,
        var match: Double? = null,
        var mbid: String? = null,
        var name: String? = null,
        @SerializedName("playcount")
        var playcount: String? = null,
        @SerializedName("toptags")
        var topTag: CommonLastFmData.TagsData? = null,
        var url: String? = null,
        var realUrl: String? = null,
        var wiki: CommonLastFmData.WikiData? = null
    ) : Data {
        override fun toString() =
            """
album: $album
attr: $attr
artist: $artist
duration: $duration
images: $images
listeners: $listeners
match: $match
mbid: $mbid
name: $name
playcount: $playcount
topTag: $topTag
url: $url
realUrl: $realUrl
wiki: $wiki
"""
    }
}
