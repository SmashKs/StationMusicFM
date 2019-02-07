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

import com.no1.taiwan.stationmusicfm.data.data.lastfm.CommonLastFmData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfo
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfo
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfo
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_DOUBLE

/**
 * A transforming mapping between [TrackInfoData.TrackData] and [TrackInfo.TrackModel].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class TrackDMapper(
    private val albumMapper: AlbumDMapper,
    private val artistMapper: ArtistDMapper,
    private val attrMapper: AttrDMapper,
    private val imageMapper: ImageDMapper,
    private val streamMapper: StreamDMapper,
    private val tagMapper: TagDMapper,
    private val wikiMapper: WikiDMapper
) : Mapper<TrackInfoData.TrackData, TrackInfo.TrackModel> {
    override fun toModelFrom(data: TrackInfoData.TrackData): TrackInfo.TrackModel {
        return data.run {
            TrackInfo.TrackModel(streamable?.let(streamMapper::toModelFrom) ?: CommonLastFmModel.StreamableModel())
                .apply {
                    album = data.album?.let(albumMapper::toModelFrom) ?: AlbumInfo.AlbumModel()
                    attr = data.attr?.let(attrMapper::toModelFrom) ?: CommonLastFmModel.AttrModel()
                    artist = data.artist?.let(artistMapper::toModelFrom) ?: ArtistInfo.ArtistModel()
                    duration = data.duration.orEmpty()
                    images = data.images?.map(imageMapper::toModelFrom).orEmpty()
                    listeners = data.listeners.orEmpty()
                    match = data.match ?: DEFAULT_DOUBLE
                    mbid = data.mbid.orEmpty()
                    name = data.name.orEmpty()
                    playcount = data.playcount.orEmpty()
                    topTags = data.topTag?.tags?.map(tagMapper::toModelFrom).orEmpty()
                    url = data.url.orEmpty()
                    realUrl = data.realUrl.orEmpty()
                    wiki = data.wiki?.let(wikiMapper::toModelFrom) ?: CommonLastFmModel.WikiModel()
                }
        }
    }

    override fun toDataFrom(model: TrackInfo.TrackModel): TrackInfoData.TrackData {
        return model.run {
            TrackInfoData.TrackData(streamMapper.toDataFrom(streamable)).apply {
                album = model.album.let(albumMapper::toDataFrom)
                attr = model.attr.let(attrMapper::toDataFrom)
                artist = model.artist.let(artistMapper::toDataFrom)
                duration = model.duration
                images = model.images.map(imageMapper::toDataFrom)
                listeners = model.listeners
                match = model.match
                mbid = model.mbid
                name = model.name
                playcount = model.playcount
                topTag = CommonLastFmData.TagsData(model.topTags.map(tagMapper::toDataFrom), null)
                url = model.url
                realUrl = model.realUrl
                wiki = model.wiki.let(wikiMapper::toDataFrom)
            }
        }
    }
}
