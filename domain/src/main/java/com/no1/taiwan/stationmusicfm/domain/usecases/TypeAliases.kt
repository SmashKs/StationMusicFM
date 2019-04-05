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

package com.no1.taiwan.stationmusicfm.domain.usecases

import com.no1.taiwan.stationmusicfm.domain.DeferredUsecase
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TagInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.others.RankingIdModel
import com.no1.taiwan.stationmusicfm.domain.models.others.SearchHistModel
import com.no1.taiwan.stationmusicfm.domain.models.playlist.LocalMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.playlist.PlaylistInfoModel
import com.no1.taiwan.stationmusicfm.domain.usecases.history.AddOrUpdateSearchHistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.history.DeleteSearchHistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.history.FetchSearchHistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistPhotoRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistTopAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopTagRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchSimilarArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchSimilarTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.AddRankIdsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchHotListRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankIdsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchSongsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.UpdateRankItemRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.AddOrUpdateLocalMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.AddPlaylistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.DeletePlaylistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.FetchListenedHistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.FetchPlaylistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.FetchTypeOfHistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.UpdatePlaylistsRespCase

// MusicBank

typealias FetchRankMusicReq = FetchRankMusicRespCase.Request
typealias FetchSongsReq = FetchSongsRespCase.Request
typealias FetchMusicReq = FetchMusicRespCase.Request
typealias FetchHotListReq = FetchHotListRespCase.Request

typealias FetchRankMusicCase = DeferredUsecase<MusicInfoModel.MusicModel, FetchRankMusicReq>
typealias FetchSongsCase = DeferredUsecase<CommonMusicModel.PlayListModel, FetchSongsReq>
typealias FetchMusicCase = DeferredUsecase<MusicInfoModel.MusicModel, FetchMusicReq>
typealias FetchHotListCase = DeferredUsecase<HotPlaylistModel.HotListModel, FetchHotListReq>

// LastFm

typealias FetchAlbumReq = FetchAlbumRespCase.Request
typealias FetchArtistReq = FetchArtistRespCase.Request
typealias FetchArtistPhotoReq = FetchArtistPhotoRespCase.Request
typealias FetchArtistTopAlbumReq = FetchArtistTopAlbumRespCase.Request
typealias FetchArtistTopTrackReq = FetchArtistTopTrackRespCase.Request
typealias FetchChartTopArtistReq = FetchChartTopArtistRespCase.Request
typealias FetchChartTopTagReq = FetchChartTopTagRespCase.Request
typealias FetchChartTopTrackReq = FetchChartTopTrackRespCase.Request
typealias FetchSimilarArtistReq = FetchSimilarArtistRespCase.Request
typealias FetchSimilarTrackReq = FetchSimilarTrackRespCase.Request
typealias FetchTagReq = FetchTagRespCase.Request
typealias FetchTagTopAlbumReq = FetchTagTopAlbumRespCase.Request
typealias FetchTagTopArtistReq = FetchTagTopArtistRespCase.Request
typealias FetchTagTopTrackReq = FetchTagTopTrackRespCase.Request
typealias FetchTrackReq = FetchTrackRespCase.Request

typealias FetchAlbumCase = DeferredUsecase<AlbumInfoModel.AlbumModel, FetchAlbumReq>
typealias FetchArtistCase = DeferredUsecase<ArtistInfoModel.ArtistModel, FetchArtistReq>
typealias FetchArtistPhotoCase = DeferredUsecase<ArtistInfoModel.ArtistPhotosModel, FetchArtistPhotoReq>
typealias FetchArtistTopAlbumCase = DeferredUsecase<CommonLastFmModel.TopAlbumsModel, FetchArtistTopAlbumReq>
typealias FetchArtistTopTrackCase = DeferredUsecase<TrackInfoModel.TracksWithStreamableModel, FetchArtistTopTrackReq>
typealias FetchChartTopArtistCase = DeferredUsecase<ArtistInfoModel.ArtistsModel, FetchChartTopArtistReq>
typealias FetchChartTopTagCase = DeferredUsecase<CommonLastFmModel.TagsModel, FetchChartTopTagReq>
typealias FetchChartTopTrackCase = DeferredUsecase<TrackInfoModel.TracksModel, FetchChartTopTrackReq>
typealias FetchSimilarArtistCase = DeferredUsecase<ArtistInfoModel.ArtistsModel, FetchSimilarArtistReq>
typealias FetchSimilarTrackCase = DeferredUsecase<TrackInfoModel.TracksModel, FetchSimilarTrackReq>
typealias FetchTagCase = DeferredUsecase<TagInfoModel.TagModel, FetchTagReq>
typealias FetchTagTopAlbumCase = DeferredUsecase<CommonLastFmModel.TopAlbumsModel, FetchTagTopAlbumReq>
typealias FetchTagTopArtistCase = DeferredUsecase<ArtistInfoModel.ArtistsModel, FetchTagTopArtistReq>
typealias FetchTagTopTrackCase = DeferredUsecase<TrackInfoModel.TracksModel, FetchTagTopTrackReq>
typealias FetchTrackCase = DeferredUsecase<TrackInfoModel.TrackModel, FetchTrackReq>

// Others

typealias AddRankIdsReq = AddRankIdsRespCase.Request
typealias FetchRankIdsReq = FetchRankIdsRespCase.Request
typealias AddOrUpdateSearchHistReq = AddOrUpdateSearchHistRespCase.Request
typealias DeleteSearchHistReq = DeleteSearchHistRespCase.Request
typealias FetchSearchHistsReq = FetchSearchHistsRespCase.Request
typealias UpdateRankItemReq = UpdateRankItemRespCase.Request

typealias AddRankIdsCase = DeferredUsecase<Boolean, AddRankIdsReq>
typealias FetchRankIdsCase = DeferredUsecase<List<RankingIdModel>, FetchRankIdsReq>
typealias AddOrUpdateSearchHistCase = DeferredUsecase<Boolean, AddOrUpdateSearchHistReq>
typealias DeleteSearchHistCase = DeferredUsecase<Boolean, DeleteSearchHistReq>
typealias FetchSearchHistsCase = DeferredUsecase<List<SearchHistModel>, FetchSearchHistsReq>
typealias UpdateRankItemCase = DeferredUsecase<Boolean, UpdateRankItemReq>

// Playlist

typealias AddOrUpdateLocalMusicReq = AddOrUpdateLocalMusicRespCase.Request
typealias FetchListenedHistReq = FetchListenedHistsRespCase.Request
typealias FetchTypeOfHistsReq = FetchTypeOfHistsRespCase.Request
typealias FetchPlaylistsReq = FetchPlaylistsRespCase.Request
typealias AddPlaylistsReq = AddPlaylistsRespCase.Request
typealias UpdatePlaylistsReq = UpdatePlaylistsRespCase.Request
typealias DeletePlaylistsReq = DeletePlaylistsRespCase.Request

typealias AddOrUpdateLocalMusicCase = DeferredUsecase<Boolean, AddOrUpdateLocalMusicReq>
typealias FetchListenedHistCase = DeferredUsecase<List<LocalMusicModel>, FetchListenedHistReq>
typealias FetchTypeOfHistsCase = DeferredUsecase<List<LocalMusicModel>, FetchTypeOfHistsReq>
typealias FetchPlaylistsCase = DeferredUsecase<List<PlaylistInfoModel>, FetchPlaylistsReq>
typealias AddPlaylistsCase = DeferredUsecase<Boolean, AddPlaylistsReq>
typealias UpdatePlaylistsCase = DeferredUsecase<Boolean, UpdatePlaylistsReq>
typealias DeletePlaylistsCase = DeferredUsecase<Boolean, DeletePlaylistsReq>
