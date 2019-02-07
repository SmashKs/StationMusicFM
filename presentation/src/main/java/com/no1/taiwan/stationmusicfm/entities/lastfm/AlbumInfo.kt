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

package com.no1.taiwan.stationmusicfm.entities.lastfm

import com.no1.taiwan.stationmusicfm.entities.Entity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

object AlbumInfo {
    data class AlbumEntity(
        val artist: String = DEFAULT_STR,
        val playCount: String = DEFAULT_STR
    ) : BaseAlbumEntity(), Entity

    data class TopAlbumsEntity(
        val albums: List<AlbumInfo.AlbumWithArtistEntity> = emptyList(),
        val attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity()
    ) : Entity

    data class AlbumWithArtistEntity(
        val artist: ArtistInfo.ArtistEntity = ArtistInfo.ArtistEntity(),
        val playCount: String = DEFAULT_STR,
        val index: Int = 0
    ) : BaseAlbumEntity(), Entity

    open class BaseAlbumEntity(
        var attr: CommonLastFmEntity.AttrEntity = CommonLastFmEntity.AttrEntity(),
        var images: List<CommonLastFmEntity.ImageEntity> = emptyList(),
        var listeners: String = DEFAULT_STR,
        var mbid: String = DEFAULT_STR,
        var name: String = DEFAULT_STR,
        var tags: List<TagInfo.TagEntity> = emptyList(),
        var title: String = DEFAULT_STR,
        var tracks: List<TrackInfoEntity.TrackEntity> = emptyList(),
        var url: String = DEFAULT_STR,
        var wiki: CommonLastFmEntity.WikiEntity = CommonLastFmEntity.WikiEntity()
    ) : Entity
}
