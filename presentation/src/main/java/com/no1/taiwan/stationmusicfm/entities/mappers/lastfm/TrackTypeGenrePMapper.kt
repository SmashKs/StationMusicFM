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

package com.no1.taiwan.stationmusicfm.entities.mappers.lastfm

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.entities.TrackTypeGenrePreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity

/**
 * A transforming mapping between [TrackInfoModel.TrackModel] and [TrackInfoEntity.TrackTypeGenreEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class TrackTypeGenrePMapper(
    private val artistMapper: ArtistPMapper,
    private val attrMapper: AttrPMapper,
    private val imageMapper: ImagePMapper,
    private val streamMapper: StreamPMapper,
    private val tagMapper: TagPMapper,
    private val trackMapper: TrackPMapper,
    private val wikiMapper: WikiPMapper
) : TrackTypeGenrePreziMap {
    // OPTIMIZE(jieyi): 2019-02-07 Here will happened recursive dependency if as an argument.
    private val albumMapper = AlbumPMapper(attrMapper, imageMapper, tagMapper, trackMapper, wikiMapper)

    override fun toEntityFrom(model: TrackInfoModel.TrackModel): TrackInfoEntity.TrackTypeGenreEntity {
        return model.run {
            TrackInfoEntity.TrackTypeGenreEntity().apply {
                streamable = model.streamable.let(streamMapper::toEntityFrom)
                album = model.album.let(albumMapper::toEntityFrom)
                attr = model.attr.let(attrMapper::toEntityFrom)
                artist = model.artist.let(artistMapper::toEntityFrom)
                duration = model.duration
                images = model.images.map(imageMapper::toEntityFrom)
                listeners = model.listeners
                match = model.match
                mbid = model.mbid
                name = model.name
                playcount = model.playcount
                topTags = model.topTags.map(tagMapper::toEntityFrom)
                url = model.url
                realUrl = model.realUrl
                wiki = model.wiki.let(wikiMapper::toEntityFrom)
            }
        }
    }

    override fun toModelFrom(entity: TrackInfoEntity.TrackTypeGenreEntity) = throw UnsupportedOperationException()
}
