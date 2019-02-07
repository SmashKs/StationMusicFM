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

package com.no1.taiwan.stationmusicfm.entities.mappers.musicbank

import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.entities.MvPreziMap
import com.no1.taiwan.stationmusicfm.entities.SongPreziMap
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity

/**
 * A transforming mapping between [CommonMusicModel.SongModel] and [CommonMusicEntity.SongEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class SongPMapper(
    private val mvMapper: MvPreziMap
) : SongPreziMap {
    override fun toEntityFrom(model: CommonMusicModel.SongModel) = model.run {
        CommonMusicEntity.SongEntity(artist,
                                     cdnCoverUrl,
                                     copyrightType,
                                     coverURL,
                                     flag,
                                     length,
                                     lyricURL,
                                     mvMapper.toEntityFrom(mv),
                                     oriCoverUrl,
                                     otherSources,
                                     shareUri,
                                     sid,
                                     songIdExt,
                                     title,
                                     uploader,
                                     url)
    }

    override fun toModelFrom(entity: CommonMusicEntity.SongEntity) = entity.run {
        CommonMusicModel.SongModel(artist,
                                   cdnCoverUrl,
                                   copyrightType,
                                   coverURL,
                                   flag,
                                   length,
                                   lyricURL,
                                   mvMapper.toModelFrom(mv),
                                   oriCoverUrl,
                                   otherSources,
                                   shareUri,
                                   sid,
                                   songIdExt,
                                   title,
                                   uploader,
                                   url)
    }
}
