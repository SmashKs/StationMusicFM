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

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.entities.AlbumWithArtistTypeGenrePreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity

/**
 * A transforming mapping between [AlbumInfoModel.AlbumWithArtistModel] and [AlbumInfoEntity.AlbumWithArtistTypeGenreEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class AlbumWithArtistTypeGenrePMapper(
    private val artistMapper: ArtistPMapper,
    private val attrMapper: AttrPMapper,
    private val imageMapper: ImagePMapper,
    private val tagMapper: TagPMapper,
    private val trackMapper: TrackPMapper,
    private val wikiMapper: WikiPMapper
) : AlbumWithArtistTypeGenrePreziMap {
    override fun toEntityFrom(model: AlbumInfoModel.AlbumWithArtistModel) = model.run {
        AlbumInfoEntity.AlbumWithArtistTypeGenreEntity().apply {
            artist = model.artist.let(artistMapper::toEntityFrom)
            playCount = model.playCount
            index = model.index
            attr = model.attr.let(attrMapper::toEntityFrom)
            images = model.images.map(imageMapper::toEntityFrom)
            listeners = model.listeners
            mbid = model.mbid
            name = model.name
            tags = model.tags.map(tagMapper::toEntityFrom)
            title = model.title
            tracks = model.tracks.map(trackMapper::toEntityFrom)
            url = model.url
            wiki = model.wiki.let(wikiMapper::toEntityFrom)
        }
    }

    override fun toModelFrom(entity: AlbumInfoEntity.AlbumWithArtistTypeGenreEntity) = entity.run {
        AlbumInfoModel.AlbumWithArtistModel(artistMapper.toModelFrom(artist), playCount, index)
            .apply {
                attr = attrMapper.toModelFrom(entity.attr)
                images = entity.images.map(imageMapper::toModelFrom)
                listeners = entity.listeners
                mbid = entity.mbid
                name = entity.name
                tags = entity.tags.map(tagMapper::toModelFrom)
                title = entity.title
                tracks = entity.tracks.map(trackMapper::toModelFrom)
                url = entity.url
                wiki = wikiMapper.toModelFrom(entity.wiki)
            }
    }
}
