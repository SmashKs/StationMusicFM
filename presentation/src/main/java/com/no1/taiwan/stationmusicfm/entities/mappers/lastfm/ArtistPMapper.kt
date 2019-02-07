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

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel
import com.no1.taiwan.stationmusicfm.entities.ArtistPreziMap
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity

/**
 * A transforming mapping between [ArtistInfoModel.ArtistModel] and [ArtistInfoEntity.ArtistEntity]. The different layers have
 * their own data objects, the objects should transform and fit each layers.
 */
class ArtistPMapper(
    private val bioMapper: BioPMapper,
    private val imageMapper: ImagePMapper,
    private val statsMapper: StatsPMapper,
    private val tagMapper: TagPMapper
) : ArtistPreziMap {
    override fun toEntityFrom(model: ArtistInfoModel.ArtistModel): ArtistInfoEntity.ArtistEntity = model.run {
        ArtistInfoEntity.ArtistEntity(name,
                                      mbid,
                                      match,
                                      url,
                                      images.map(imageMapper::toEntityFrom),
                                      streamable,
                                      listeners,
                                      onTour,
                                      playCount,
                                      stats.let(statsMapper::toEntityFrom),
                                      similars.map(this@ArtistPMapper::toEntityFrom),
                                      tags.map(tagMapper::toEntityFrom),
                                      bio.let(bioMapper::toEntityFrom))
    }

    override fun toModelFrom(entity: ArtistInfoEntity.ArtistEntity): ArtistInfoModel.ArtistModel = entity.run {
        ArtistInfoModel.ArtistModel(name,
                                    mbid,
                                    match,
                                    url,
                                    images.map(imageMapper::toModelFrom),
                                    streamable,
                                    listeners,
                                    onTour,
                                    playCount,
                                    statsMapper.toModelFrom(stats),
                                    similars.map(this@ArtistPMapper::toModelFrom),
                                    tags.map(tagMapper::toModelFrom),
                                    bioMapper.toModelFrom(bio))
    }
}
