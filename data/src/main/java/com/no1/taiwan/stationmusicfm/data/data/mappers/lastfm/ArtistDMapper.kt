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

package com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm

import com.no1.taiwan.stationmusicfm.data.data.ArtistDataMap
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.CommonLastFmData
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel

/**
 * A transforming mapping between [ArtistInfoData.ArtistData] and [ArtistInfoModel.ArtistModel]. The different layers have
 * their own data objects, the objects should transform and fit each layers.
 */
class ArtistDMapper(
    private val bioMapper: BioDMapper,
    private val imageMapper: ImageDMapper,
    private val statsMapper: StatsDMapper,
    private val tagMapper: TagDMapper
) : ArtistDataMap {
    override fun toModelFrom(data: ArtistInfoData.ArtistData): ArtistInfoModel.ArtistModel {
        return data.run {
            ArtistInfoModel.ArtistModel(name.orEmpty(),
                                        mbid.orEmpty(),
                                        match.orEmpty(),
                                        url.orEmpty(),
                                        images?.map(imageMapper::toModelFrom).orEmpty(),
                                        streamable.orEmpty(),
                                        listeners.orEmpty(),
                                        onTour.orEmpty(),
                                        playCount.orEmpty(),
                                        stats?.let(statsMapper::toModelFrom) ?: ArtistInfoModel.StatsModel(),
                                        similar?.artists?.map(this@ArtistDMapper::toModelFrom).orEmpty(),
                                        tags?.tags?.map(tagMapper::toModelFrom).orEmpty(),
                                        bio?.let(bioMapper::toModelFrom) ?: ArtistInfoModel.BioModel())
        }
    }

    override fun toDataFrom(model: ArtistInfoModel.ArtistModel): ArtistInfoData.ArtistData {
        return model.run {
            ArtistInfoData.ArtistData(name,
                                      mbid,
                                      match,
                                      url,
                                      images.map(imageMapper::toDataFrom),
                                      streamable,
                                      listeners,
                                      onTour,
                                      playCount,
                                      statsMapper.toDataFrom(stats),
                                      ArtistInfoData.SimilarData(similars.map(this@ArtistDMapper::toDataFrom)),
                                      CommonLastFmData.TagsData(tags.map(tagMapper::toDataFrom), null),
                                      bioMapper.toDataFrom(bio))
        }
    }
}
