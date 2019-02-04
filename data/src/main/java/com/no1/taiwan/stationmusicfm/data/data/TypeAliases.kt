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

package com.no1.taiwan.stationmusicfm.data.data

import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.data.data.rank.CommonMusicData
import com.no1.taiwan.stationmusicfm.data.data.rank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.rank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.rank.RankChartData
import com.no1.taiwan.stationmusicfm.domain.models.rank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.rank.RankChartModel

typealias DataMapper = Mapper<*, *>
typealias DataMapperPool = Map<Class<out DataMapper>, DataMapper>

// Mappers

typealias HotListDataMap = Mapper<HotPlaylistData.HotListInfoData, HotPlaylistModel.HotListInfoModel>
typealias MusicDataMap = Mapper<MusicInfoData.MusicData, MusicInfoModel.MusicModel>
typealias MvDataMap = Mapper<CommonMusicData.MvData, CommonMusicModel.MvModel>
typealias PlaylistDataMap = Mapper<CommonMusicData.PlayListData, CommonMusicModel.PlayListModel>
typealias RankChartDataMap = Mapper<RankChartData, RankChartModel>
typealias SongDataMap = Mapper<CommonMusicData.SongData, CommonMusicModel.SongModel>
typealias UserDataMap = Mapper<CommonMusicData.UserData, CommonMusicModel.UserModel>
