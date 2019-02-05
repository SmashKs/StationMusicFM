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

package com.no1.taiwan.stationmusicfm.data.data.mappers

import com.no1.taiwan.stationmusicfm.data.data.HotListDataMap
import com.no1.taiwan.stationmusicfm.data.data.SongListDataMap
import com.no1.taiwan.stationmusicfm.data.data.musicbank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel

/**
 * A transforming mapping between [HotPlaylistData.HotListData] and [HotPlaylistModel.HotListModel]. The different layers have
 * their own data objects, the objects should transform and fit each layers.
 */
class HotListDMapper(
    private val songListMapper: SongListDataMap
) : HotListDataMap {
    override fun toModelFrom(data: HotPlaylistData.HotListData) = data.run {
        HotPlaylistModel.HotListModel(hasMore, playlists.map(songListMapper::toModelFrom))
    }

    override fun toDataFrom(model: HotPlaylistModel.HotListModel) = model.run {
        HotPlaylistData.HotListData(hasMore, playlists.map(songListMapper::toDataFrom))
    }
}
