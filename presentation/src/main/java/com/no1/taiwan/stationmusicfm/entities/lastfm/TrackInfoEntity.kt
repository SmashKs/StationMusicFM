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

package com.no1.taiwan.stationmusicfm.entities.lastfm

import com.no1.taiwan.stationmusicfm.entities.Entity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class TrackInfoEntity(
    val track: TrackEntity
) : Entity {
    data class TrackEntity(
        val streamable: CommonLastFmEntity.StreamableEntity = CommonLastFmEntity.StreamableEntity()
    ) : BaseTrackEntity(), Entity

    data class TrackWithStreamableEntity(
        val streamable: String = DEFAULT_STR
    ) : BaseTrackEntity(), Entity

    open class BaseTrackEntity(
        var album: AlbumInfoEntity.AlbumEntity = AlbumInfoEntity.AlbumEntity(),
        var attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity(),
        var artist: ArtistInfoEntity.ArtistEntity = ArtistInfoEntity.ArtistEntity(),
        var duration: String = DEFAULT_STR,
        var images: List<CommonLastFmEntity.ImageEntity> = emptyList(),
        var listeners: String = DEFAULT_STR,
        var match: Double = .0,
        var mbid: String = DEFAULT_STR,
        var name: String = DEFAULT_STR,
        var playcount: String = DEFAULT_STR,
        var topTags: List<TagInfoEntity.TagEntity> = emptyList(),
        var url: String = DEFAULT_STR,
        var realUrl: String = DEFAULT_STR,
        var wiki: CommonLastFmEntity.WikiEntity = CommonLastFmEntity.WikiEntity()
    ) : Entity {
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
topTags: $topTags
url: $url
realUrl: $realUrl
wiki: $wiki
"""
    }
}
