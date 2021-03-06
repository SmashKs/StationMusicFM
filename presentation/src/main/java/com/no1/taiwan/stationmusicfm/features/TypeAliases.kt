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

package com.no1.taiwan.stationmusicfm.features

import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.BriefRankEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.entities.others.SearchHistoryEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.ext.Quadruple
import com.no1.taiwan.stationmusicfm.ext.Quintuple

typealias ArtistMixInfo = Quintuple<ArtistInfoEntity.ArtistEntity, AlbumInfoEntity.TopAlbumsEntity, ArtistInfoEntity.ArtistsSimilarEntity, TrackInfoEntity.TracksWithStreamableEntity, ArtistInfoEntity.PhotosEntity>
typealias GenreMixInfo = Quadruple<TagInfoEntity.TagEntity, ArtistInfoEntity.ArtistsEntity, AlbumInfoEntity.TopAlbumsEntity, TrackInfoEntity.TracksTypeGenreEntity>

// ***

typealias SearchHistories = List<SearchHistoryEntity>
typealias RankingIds = List<RankingIdEntity>
typealias Rankings = List<BriefRankEntity>
typealias RankingIdsForChart = List<RankingIdForChartItem>
typealias Playlists = List<PlaylistInfoEntity>
typealias LocalMusics = List<LocalMusicEntity>
