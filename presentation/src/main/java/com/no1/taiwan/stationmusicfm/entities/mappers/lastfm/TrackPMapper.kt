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
import com.no1.taiwan.stationmusicfm.entities.TrackPreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity

/**
 * A transforming mapping between [TrackInfoModel.TrackModel] and [TrackInfoEntity.TrackEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class TrackPMapper(
    private val artistMapper: ArtistPMapper,
    private val attrMapper: AttrPMapper,
    private val imageMapper: ImagePMapper,
    private val streamMapper: StreamPMapper,
    private val tagMapper: TagPMapper,
    private val wikiMapper: WikiPMapper
) : TrackPreziMap {
    // OPTIMIZE(jieyi): 2019-02-07 Here will happened recursive dependency if as an argument.
    private val albumMapper = AlbumPMapper(attrMapper, imageMapper, tagMapper, this, wikiMapper)

    override fun toEntityFrom(model: TrackInfoModel.TrackModel): TrackInfoEntity.TrackEntity {
        return model.run {
            TrackInfoEntity.TrackEntity(streamable.let(streamMapper::toEntityFrom))
                .apply {
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

    override fun toModelFrom(entity: TrackInfoEntity.TrackEntity): TrackInfoModel.TrackModel {
        return entity.run {
            TrackInfoModel.TrackModel(streamMapper.toModelFrom(streamable)).apply {
                album = entity.album.let(albumMapper::toModelFrom)
                attr = entity.attr.let(attrMapper::toModelFrom)
                artist = entity.artist.let(artistMapper::toModelFrom)
                duration = entity.duration
                images = entity.images.map(imageMapper::toModelFrom)
                listeners = entity.listeners
                match = entity.match
                mbid = entity.mbid
                name = entity.name
                playcount = entity.playcount
                topTags = entity.topTags.map(tagMapper::toModelFrom)
                url = entity.url
                realUrl = entity.realUrl
                wiki = entity.wiki.let(wikiMapper::toModelFrom)
            }
        }
    }
}
