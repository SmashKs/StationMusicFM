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

import com.devrapid.kotlinshaver.trimMarginAndNewLine
import com.no1.taiwan.stationmusicfm.entities.Entity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiVisitable

object TrackInfoEntity {
    open class TrackEntity(
        var streamable: CommonLastFmEntity.StreamableEntity = CommonLastFmEntity.StreamableEntity()
    ) : BaseTrackEntity(), Entity, MusicMultiVisitable {
        override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)

        override fun toString() = """
            |${this::class.java.simpleName}(
            |streamable=$streamable
            |${super.toString()})
            |""".trimMarginAndNewLine()
    }

    /**
     * The same as [TrackEntity] but this is for others viewholder type.
     */
    class TrackTypeGenreEntity : TrackEntity() {
        override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)
    }

    data class TracksTypeGenreEntity(
        val tracks: List<TrackTypeGenreEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    data class TracksEntity(
        val tracks: List<TrackEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    data class TracksWithStreamableEntity(
        val tracks: List<TrackWithStreamableEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    data class TrackWithStreamableEntity(
        val streamable: String = DEFAULT_STR
    ) : BaseTrackEntity(), Entity, MusicMultiVisitable {
        override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)
    }

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
        override fun toString() = """
            |album: $album
            |attr: $attr
            |artist: $artist
            |duration: $duration
            |images: $images
            |listeners: $listeners
            |match: $match
            |mbid: $mbid
            |name: $name
            |playcount: $playcount
            |topTags: $topTags
            |url: $url
            |realUrl: $realUrl
            |wiki: $wiki
            |""".trimMarginAndNewLine()
    }
}
