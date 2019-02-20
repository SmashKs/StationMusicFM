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

import com.no1.taiwan.stationmusicfm.data.data.lastfm.AlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistPhotosData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistTopTrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.CommonLastFmData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TagInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopArtistInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper
import com.no1.taiwan.stationmusicfm.data.data.musicbank.CommonMusicData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.RankChartData
import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TagInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.RankChartModel
import com.no1.taiwan.stationmusicfm.domain.models.others.RankingIdModel

typealias DataMapper = Mapper<*, *>
typealias DataMapperPool = Map<Class<out DataMapper>, DataMapper>

// Mappers
// MusicBank

typealias HotListDataMap = Mapper<HotPlaylistData.HotListData, HotPlaylistModel.HotListModel>
typealias MusicDataMap = Mapper<MusicInfoData.MusicData, MusicInfoModel.MusicModel>
typealias MvDataMap = Mapper<CommonMusicData.MvData, CommonMusicModel.MvModel>
typealias SongListDataMap = Mapper<CommonMusicData.PlayListData, CommonMusicModel.PlayListModel>
typealias RankChartDataMap = Mapper<RankChartData, RankChartModel>
typealias SongDataMap = Mapper<CommonMusicData.SongData, CommonMusicModel.SongModel>
typealias UserDataMap = Mapper<CommonMusicData.UserData, CommonMusicModel.UserModel>

// LastFm

typealias AlbumDataMap = Mapper<AlbumInfoData.AlbumData, AlbumInfoModel.AlbumModel>
typealias AlbumWithArtistDataMap = Mapper<AlbumInfoData.AlbumWithArtistData, AlbumInfoModel.AlbumWithArtistModel>
typealias ArtistDataMap = Mapper<ArtistInfoData.ArtistData, ArtistInfoModel.ArtistModel>
typealias ArtistsDataMap = Mapper<TopArtistInfoData.ArtistsData, ArtistInfoModel.ArtistsModel>
typealias ArtistPhotoDataMap = Mapper<ArtistPhotosData.ArtistPhotoData, ArtistInfoModel.ArtistPhotoModel>
typealias ArtistPhotosDataMap = Mapper<ArtistPhotosData, ArtistInfoModel.ArtistPhotosModel>
typealias AttrDataMap = Mapper<CommonLastFmData.AttrData, CommonLastFmModel.AttrModel>
typealias BioDataMap = Mapper<ArtistInfoData.BioData, ArtistInfoModel.BioModel>
typealias ImageDataMap = Mapper<CommonLastFmData.ImageData, CommonLastFmModel.ImageModel>
typealias LinkDataMap = Mapper<ArtistInfoData.LinkData, ArtistInfoModel.LinkModel>
typealias StatsDataMap = Mapper<ArtistInfoData.StatsData, ArtistInfoModel.StatsModel>
typealias StreamDataMap = Mapper<CommonLastFmData.StreamableData, CommonLastFmModel.StreamableModel>
typealias TagDataMap = Mapper<TagInfoData.TagData, TagInfoModel.TagModel>
typealias TagsDataMap = Mapper<CommonLastFmData.TagsData, CommonLastFmModel.TagsModel>
typealias TopAlbumDataMap = Mapper<CommonLastFmData.TopAlbumsData, CommonLastFmModel.TopAlbumsModel>
typealias TrackDataMap = Mapper<TrackInfoData.TrackData, TrackInfoModel.TrackModel>
typealias TracksDataMap = Mapper<TopTrackInfoData.TracksData, TrackInfoModel.TracksModel>
typealias TracksWithStreamableDataMap = Mapper<ArtistTopTrackInfoData.TracksWithStreamableData, TrackInfoModel.TracksWithStreamableModel>
typealias TrackWithStreamableDataMap = Mapper<TrackInfoData.TrackWithStreamableData, TrackInfoModel.TrackWithStreamableModel>
typealias WikiDataMap = Mapper<CommonLastFmData.WikiData, CommonLastFmModel.WikiModel>

// Others

typealias RankingDataMap = Mapper<RankingIdData, RankingIdModel>
