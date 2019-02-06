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

import com.no1.taiwan.stationmusicfm.data.data.lastfm.AlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel

/**
 * A transforming mapping between [AlbumInfoData.AlbumWithArtistData] and [AlbumInfoModel.AlbumWithArtistModel].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class AlbumWithArtistDMapper(
    private val artistMapper: ArtistDMapper
) : Mapper<AlbumInfoData.AlbumWithArtistData, AlbumInfoModel.AlbumWithArtistModel> {
    override fun toModelFrom(data: AlbumInfoData.AlbumWithArtistData) = data.run {
        AlbumInfoModel.AlbumWithArtistModel(artist?.let(artistMapper::toModelFrom) ?: ArtistInfoModel.ArtistModel(),
                                            playCount.orEmpty(),
                                            index)
    }

    override fun toDataFrom(model: AlbumInfoModel.AlbumWithArtistModel) = model.run {
        AlbumInfoData.AlbumWithArtistData(artistMapper.toDataFrom(artist), playCount, index)
    }
}
