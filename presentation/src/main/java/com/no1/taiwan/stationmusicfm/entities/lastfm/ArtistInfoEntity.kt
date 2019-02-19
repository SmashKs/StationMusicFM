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
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiVisitable

object ArtistInfoEntity {
    open class ArtistEntity(
        var name: String = DEFAULT_STR,
        var mbid: String = DEFAULT_STR,
        var match: String = DEFAULT_STR,
        var url: String = DEFAULT_STR,
        var images: List<CommonLastFmEntity.ImageEntity> = emptyList(),
        var streamable: String = DEFAULT_STR,
        var listeners: String = DEFAULT_STR,
        var onTour: String = DEFAULT_STR,
        var playCount: String = DEFAULT_STR,
        var stats: StatsEntity = StatsEntity(),
        var similars: List<ArtistEntity> = emptyList(),
        var tags: List<TagInfoEntity.TagEntity> = emptyList(),
        var bio: BioEntity = BioEntity()
    ) : Entity, MusicMultiVisitable {
        override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)
    }

    data class ArtistsEntity(
        val artists: List<ArtistEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    /**
     * The same as [ArtistEntity] but this is for others viewholder type.
     */
    class ArtistSimilarEntity : ArtistEntity() {
        override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)
    }

    data class ArtistsSimilarEntity(
        val artists: List<ArtistSimilarEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    data class BioEntity(
        val link: LinkEntity = LinkEntity(),
        val published: String = DEFAULT_STR,
        val summary: String = DEFAULT_STR,
        val content: String = DEFAULT_STR
    ) : Entity

    data class LinkEntity(
        val text: String = DEFAULT_STR,
        val rel: String = DEFAULT_STR,
        val href: String = DEFAULT_STR
    ) : Entity

    data class StatsEntity(
        val listeners: String = DEFAULT_STR,
        val playCount: String = DEFAULT_STR
    ) : Entity
}
