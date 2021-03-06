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

package com.no1.taiwan.stationmusicfm.entities

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TagInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.BriefRankModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.RankChartModel
import com.no1.taiwan.stationmusicfm.domain.models.others.RankingIdModel
import com.no1.taiwan.stationmusicfm.domain.models.others.SearchHistModel
import com.no1.taiwan.stationmusicfm.domain.models.playlist.LocalMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.playlist.PlaylistInfoModel
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.CommonLastFmEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.mappers.Mapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.BriefRankEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.HotPlaylistEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.MusicInfoEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.RankChartEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.entities.others.SearchHistoryEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistBottomSheetEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity

typealias PreziMapper = Mapper<*, *>
typealias PreziMapperPool = Map<Class<out PreziMapper>, PreziMapper>

// Mapper
// Music Bank

typealias HotListPreziMap = Mapper<HotPlaylistModel.HotListModel, HotPlaylistEntity.HotListEntity>
typealias MusicPreziMap = Mapper<MusicInfoModel.MusicModel, MusicInfoEntity.MusicEntity>
typealias MvPreziMap = Mapper<CommonMusicModel.MvModel, CommonMusicEntity.MvEntity>
typealias SongListPreziMap = Mapper<CommonMusicModel.PlayListModel, CommonMusicEntity.PlayListEntity>
typealias RankChartPreziMap = Mapper<RankChartModel, RankChartEntity>
typealias BriefRankPreziMap = Mapper<BriefRankModel, BriefRankEntity>
typealias SongPreziMap = Mapper<CommonMusicModel.SongModel, CommonMusicEntity.SongEntity>
typealias UserPreziMap = Mapper<CommonMusicModel.UserModel, CommonMusicEntity.UserEntity>

// LastFm

typealias AlbumPreziMap = Mapper<AlbumInfoModel.AlbumModel, AlbumInfoEntity.AlbumEntity>
typealias AlbumWithArtistPreziMap = Mapper<AlbumInfoModel.AlbumWithArtistModel, AlbumInfoEntity.AlbumWithArtistEntity>
typealias AlbumWithArtistTypeGenrePreziMap = Mapper<AlbumInfoModel.AlbumWithArtistModel, AlbumInfoEntity.AlbumWithArtistTypeGenreEntity>
typealias ArtistPreziMap = Mapper<ArtistInfoModel.ArtistModel, ArtistInfoEntity.ArtistEntity>
typealias ArtistSimilarPreziMap = Mapper<ArtistInfoModel.ArtistModel, ArtistInfoEntity.ArtistSimilarEntity>
typealias ArtistsPreziMap = Mapper<ArtistInfoModel.ArtistsModel, ArtistInfoEntity.ArtistsEntity>
typealias ArtistsSimilarPreziMap = Mapper<ArtistInfoModel.ArtistsModel, ArtistInfoEntity.ArtistsSimilarEntity>
typealias ArtistPhotoPreziMap = Mapper<ArtistInfoModel.ArtistPhotoModel, ArtistInfoEntity.PhotoEntity>
typealias ArtistPhotosPreziMap = Mapper<ArtistInfoModel.ArtistPhotosModel, ArtistInfoEntity.PhotosEntity>
typealias AttrPreziMap = Mapper<CommonLastFmModel.AttrModel, CommonLastFmEntity.AttrEntity>
typealias BioPreziMap = Mapper<ArtistInfoModel.BioModel, ArtistInfoEntity.BioEntity>
typealias ImagePreziMap = Mapper<CommonLastFmModel.ImageModel, CommonLastFmEntity.ImageEntity>
typealias LinkPreziMap = Mapper<ArtistInfoModel.LinkModel, ArtistInfoEntity.LinkEntity>
typealias StatsPreziMap = Mapper<ArtistInfoModel.StatsModel, ArtistInfoEntity.StatsEntity>
typealias StreamPreziMap = Mapper<CommonLastFmModel.StreamableModel, CommonLastFmEntity.StreamableEntity>
typealias TagPreziMap = Mapper<TagInfoModel.TagModel, TagInfoEntity.TagEntity>
typealias TagsPreziMap = Mapper<CommonLastFmModel.TagsModel, TagInfoEntity.TagsEntity>
typealias TopAlbumPreziMap = Mapper<CommonLastFmModel.TopAlbumsModel, AlbumInfoEntity.TopAlbumsEntity>
typealias TrackPreziMap = Mapper<TrackInfoModel.TrackModel, TrackInfoEntity.TrackEntity>
typealias TrackTypeGenrePreziMap = Mapper<TrackInfoModel.TrackModel, TrackInfoEntity.TrackTypeGenreEntity>
typealias TracksPreziMap = Mapper<TrackInfoModel.TracksModel, TrackInfoEntity.TracksEntity>
typealias TracksTypeGenrePreziMap = Mapper<TrackInfoModel.TracksModel, TrackInfoEntity.TracksTypeGenreEntity>
typealias TracksWithStreamablePreziMap = Mapper<TrackInfoModel.TracksWithStreamableModel, TrackInfoEntity.TracksWithStreamableEntity>
typealias TrackWithStreamablePreziMap = Mapper<TrackInfoModel.TrackWithStreamableModel, TrackInfoEntity.TrackWithStreamableEntity>
typealias WikiPreziMap = Mapper<CommonLastFmModel.WikiModel, CommonLastFmEntity.WikiEntity>

// Others

typealias RankingPreziMap = Mapper<RankingIdModel, RankingIdEntity>
typealias SearchHistoryPreziMap = Mapper<SearchHistModel, SearchHistoryEntity>

// Playlist

typealias LocalMusicPreziMap = Mapper<LocalMusicModel, LocalMusicEntity>
typealias PlaylistPreziMap = Mapper<PlaylistInfoModel, PlaylistInfoEntity>
typealias PlaylistToBottomEntityPreziMap = Mapper<PlaylistInfoModel, PlaylistBottomSheetEntity>
