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
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfo
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel

/**
 * A transforming mapping between [AlbumInfoData.AlbumData] and [AlbumInfo.AlbumModel]. The different layers have
 * their own data objects, the objects should transform and fit each layers.
 */
class AlbumDMapper(
    private val attrMapper: AttrDMapper,
    private val imageMapper: ImageDMapper,
    private val tagMapper: TagDMapper,
    private val trackMapper: TrackDMapper,
    private val wikiMapper: WikiDMapper
) : Mapper<AlbumInfoData.AlbumData, AlbumInfo.AlbumModel> {
    override fun toModelFrom(data: AlbumInfoData.AlbumData) = data.run {
        AlbumInfo.AlbumModel(artist.orEmpty(), playCount.orEmpty()).apply {
            attr = data.attr?.let(attrMapper::toModelFrom) ?: CommonLastFmModel.AttrModel()
            images = data.images?.map(imageMapper::toModelFrom).orEmpty()
            listeners = data.listeners.orEmpty()
            mbid = data.mbid.orEmpty()
            name = data.name.orEmpty()
            tags = data.tags?.map(tagMapper::toModelFrom).orEmpty()
            title = data.title.orEmpty()
            tracks = data.tracks?.tracks?.map(trackMapper::toModelFrom).orEmpty()
            url = data.url.orEmpty()
            wiki = data.wiki?.let(wikiMapper::toModelFrom) ?: CommonLastFmModel.WikiModel()
        }
    }

    override fun toDataFrom(model: AlbumInfo.AlbumModel) = model.run {
        AlbumInfoData.AlbumData(artist, playCount).apply {
            attr = attrMapper.toDataFrom(model.attr)
            images = model.images.map(imageMapper::toDataFrom)
            listeners = model.listeners
            mbid = model.mbid
            name = model.name
            tags = model.tags.map(tagMapper::toDataFrom)
            title = model.title
            tracks = TopTrackInfoData.TracksData(model.tracks.map(trackMapper::toDataFrom), null)
            url = model.url
            wiki = wikiMapper.toDataFrom(model.wiki)
        }
    }
}
