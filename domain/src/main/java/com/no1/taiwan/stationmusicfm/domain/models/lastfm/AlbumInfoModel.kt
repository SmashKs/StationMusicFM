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
import org.w3c.dom.Attr
import java.awt.Image

data class AlbumInfoModel(
    val album: AlbumModel?
) : Model {
    data class AlbumModel(
        val artist: String?,
        val playCount: String? = null
    ) : BaseAlbumModel(), Model

    data class AlbumWithArtistModel(
        val artist: ArtistInfoModel.ArtistModel?,
        val playCount: String? = null
    ) : BaseAlbumModel(), Model

    data class AlbumWithPlaycountModel(
        val artist: ArtistInfoModel.ArtistModel?,
        val playCount: Int?,
        val index: Int = -1
    ) : BaseAlbumModel(), Model

    open class BaseAlbumModel(
        val attr: Attr? = null,
        val images: List<Image>? = null,
        val listeners: String? = null,
        val mbid: String? = null,
        val name: String? = null,
        val tags: CommonLastFmModel.TagsModel? = null,
        val title: String? = null,
        val track: TopTrackInfoModel.TracksModel? = null,
        val url: String? = null,
        val wiki: CommonLastFmModel.WikiModel? = null
    ) : Model
}
