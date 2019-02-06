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

package com.no1.taiwan.stationmusicfm.domain.models.lastfm

import com.no1.taiwan.stationmusicfm.domain.models.Model
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class AlbumInfoModel(
    val album: AlbumModel = AlbumModel()
) : Model {
    data class AlbumModel(
        val artist: String = DEFAULT_STR,
        val playCount: String = DEFAULT_STR
    ) : BaseAlbumModel(), Model

    data class AlbumWithArtistModel(
        val artist: ArtistInfoModel.ArtistModel = ArtistInfoModel.ArtistModel(),
        val playCount: String = DEFAULT_STR,
        val index: Int = 0
    ) : BaseAlbumModel(), Model

    open class BaseAlbumModel(
        var attr: CommonLastFmModel.AttrModel = CommonLastFmModel.AttrModel(),
        var images: List<CommonLastFmModel.ImageModel> = emptyList(),
        var listeners: String = DEFAULT_STR,
        var mbid: String = DEFAULT_STR,
        var name: String = DEFAULT_STR,
        var tags: List<TagInfoModel.TagModel> = emptyList(),
        var title: String = DEFAULT_STR,
        var tracks: List<TrackInfoModel.TrackModel> = emptyList(),
        var url: String = DEFAULT_STR,
        var wiki: CommonLastFmModel.WikiModel = CommonLastFmModel.WikiModel()
    ) : Model
}
