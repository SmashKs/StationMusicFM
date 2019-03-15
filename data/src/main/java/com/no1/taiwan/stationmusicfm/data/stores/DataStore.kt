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

package com.no1.taiwan.stationmusicfm.data.stores

import com.no1.taiwan.stationmusicfm.data.data.lastfm.AlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistPhotosData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistSimilarData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistTopAlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistTopTrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TagInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TagTopArtistData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopAlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopArtistInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTagInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TrackInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TrackSimilarData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.HotPlaylistData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.musicbank.SongListInfoData
import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.data.data.others.SearchHistoryData
import com.no1.taiwan.stationmusicfm.data.data.playlist.PlaylistInfoData
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable

/**
 * This interface will common the all data stores.
 * Using prefix name (get), (create), (modify), (remove), (store)
 */
interface DataStore {
    //region Music Rank
    suspend fun getMusicRanking(parameterable: Parameterable): MusicInfoData

    suspend fun getMusic(parameterable: Parameterable): MusicInfoData

    suspend fun getHotPlaylist(parameterable: Parameterable): HotPlaylistData

    suspend fun getSongList(parameterable: Parameterable): SongListInfoData
    //endregion

    //region Album Data
    suspend fun getAlbumInfo(parameterable: Parameterable): AlbumInfoData
    //endregion

    //region Artist Data
    suspend fun getArtistInfo(parameterable: Parameterable): ArtistInfoData

    suspend fun getArtistTopAlbum(parameterable: Parameterable): ArtistTopAlbumInfoData

    suspend fun getArtistTopTrack(parameterable: Parameterable): ArtistTopTrackInfoData

    suspend fun getSimilarArtistInfo(parameterable: Parameterable): ArtistSimilarData

    suspend fun getArtistPhotosInfo(parameterable: Parameterable): ArtistPhotosData
    //endregion

    //region Track Data
    suspend fun getTrackInfo(parameterable: Parameterable): TrackInfoData

    suspend fun getSimilarTrackInfo(parameterable: Parameterable): TrackSimilarData
    //endregion

    //region Chart
    suspend fun getChartTopTrack(parameterable: Parameterable): TopTrackInfoData

    suspend fun getChartTopArtist(parameterable: Parameterable): TopArtistInfoData

    suspend fun getChartTopTag(parameterable: Parameterable): TopTagInfoData
    //endregion

    //region Tag Data
    suspend fun getTagInfo(parameterable: Parameterable): TagInfoData

    suspend fun getTagTopAlbum(parameterable: Parameterable): TopAlbumInfoData

    suspend fun getTagTopArtist(parameterable: Parameterable): TagTopArtistData

    suspend fun getTagTopTrack(parameterable: Parameterable): TopTrackInfoData
    //endregion

    //region Ranking
    suspend fun createRankingData(params: List<RankingIdData>): Boolean

    suspend fun getRankingData(): List<RankingIdData>

    suspend fun modifyRankingData(parameterable: Parameterable): Boolean
    //endregion

    //region Search History
    suspend fun createSearchHistory(parameterable: Parameterable): Boolean

    suspend fun getSearchHistories(parameterable: Parameterable): List<SearchHistoryData>

    suspend fun removeSearchHistory(parameterable: Parameterable): Boolean
    //endregion

    //region Playlist
    suspend fun fetchLocalMusics(parameterable: Parameterable): List<MusicInfoData>

    suspend fun addLocalMusic(parameterable: Parameterable): Boolean

    suspend fun updateLocalMusic(parameterable: Parameterable): Boolean

    suspend fun deleteLocalMusic(parameterable: Parameterable): Boolean

    suspend fun fetchPlaylists(): List<PlaylistInfoData>

    suspend fun fetchPlaylist(parameterable: Parameterable): PlaylistInfoData

    suspend fun addPlaylist(parameterable: Parameterable): Boolean

    suspend fun updatePlaylist(parameterable: Parameterable): Boolean

    suspend fun deletePlaylist(parameterable: Parameterable): Boolean
    //endregion
}
