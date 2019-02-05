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

data class TrackInfoModel(
    val track: TrackModel?
) : Model {
    data class TrackModel(
        val streamable: CommonLastFmModel.StreamableModel?
    ) : BaseTrackModel(), Model

    data class TrackWithStreamableStringModel(
        val streamable: String?
    ) : BaseTrackModel(), Model

    open class BaseTrackModel(
        val album: AlbumInfoModel.AlbumModel? = null,
        val attr: CommonLastFmModel.AttrModel? = null,
        val artist: ArtistInfoModel.ArtistModel? = null,
        val duration: String? = null,
        val images: List<CommonLastFmModel.ImageModel>? = null,
        val listeners: String? = null,
        val match: Double? = null,
        val mbid: String? = null,
        val name: String? = null,
        val playcount: String? = null,
        val topTag: CommonLastFmModel.TagsModel? = null,
        val url: String? = null,
        val realUrl: String? = null,
        val wiki: CommonLastFmModel.WikiModel? = null
    ) : Model {
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
