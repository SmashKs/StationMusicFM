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

package com.no1.taiwan.stationmusicfm.entities.mappers

import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.entities.PlaylistPreziMap
import com.no1.taiwan.stationmusicfm.entities.SongPreziMap
import com.no1.taiwan.stationmusicfm.entities.UserPreziMap
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity

/**
 * A transforming mapping between [CommonMusicModel.PlayListModel] and [CommonMusicEntity.PlayListEntity].
 * The different layers have their own data objects, the objects should transform and fit each layers.
 */
class PlaylistMapper(
    private val songMapper: SongPreziMap,
    private val userMapper: UserPreziMap
) : PlaylistPreziMap {
    override fun toEntityFrom(model: CommonMusicModel.PlayListModel) = model.run {
        CommonMusicEntity.PlayListEntity(commentCount,
                                         favCount,
                                         hasFav,
                                         isCoverModified,
                                         permission,
                                         playedCount,
                                         shareCount,
                                         shareLink,
                                         shareUri,
                                         songListCover,
                                         songListDesc,
                                         songListId,
                                         songListName,
                                         songListType,
                                         songNum,
                                         songs.map(songMapper::toEntityFrom),
                                         tagIds,
                                         tags,
                                         userMapper.toEntityFrom(user))
    }

    override fun toModelFrom(entity: CommonMusicEntity.PlayListEntity) = entity.run {
        CommonMusicModel.PlayListModel(commentCount,
                                       favCount,
                                       hasFav,
                                       isCoverModified,
                                       permission,
                                       playedCount,
                                       shareCount,
                                       shareLink,
                                       shareUri,
                                       songListCover,
                                       songListDesc,
                                       songListId,
                                       songListName,
                                       songListType,
                                       songNum,
                                       songs.map(songMapper::toModelFrom),
                                       tagIds,
                                       tags,
                                       userMapper.toModelFrom(user))
    }
}
