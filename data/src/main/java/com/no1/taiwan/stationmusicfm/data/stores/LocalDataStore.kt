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

import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.castOrNull
import com.no1.taiwan.stationmusicfm.data.data.mappers.others.SearchHistoryDMapper
import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.data.data.others.SearchHistoryData
import com.no1.taiwan.stationmusicfm.data.data.playlist.LocalMusicData
import com.no1.taiwan.stationmusicfm.data.data.playlist.PlaylistInfoData
import com.no1.taiwan.stationmusicfm.data.local.services.ListenHistoryDao
import com.no1.taiwan.stationmusicfm.data.local.services.LocalMusicDao
import com.no1.taiwan.stationmusicfm.data.local.services.PlaylistDao
import com.no1.taiwan.stationmusicfm.data.local.services.RankingDao
import com.no1.taiwan.stationmusicfm.data.local.services.SearchingHistoryDao
import com.no1.taiwan.stationmusicfm.domain.models.others.SearchHistModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.parameters.history.SearchHistParams.Companion.PARAM_NAME_KEYWORD
import com.no1.taiwan.stationmusicfm.domain.parameters.history.SearchHistParams.Companion.PARAM_NAME_KEYWORD_MODEL
import com.no1.taiwan.stationmusicfm.domain.parameters.history.SearchHistParams.Companion.PARAM_NAME_LIMIT
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams.Companion.PARAM_NAME_NUMBER_OF_TRACK
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams.Companion.PARAM_NAME_RANK_ID
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams.Companion.PARAM_NAME_TOP_TRACK_URI
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_COVER_URI
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_DURATION
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_HAS_OWN
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_LOCAL_TRACK_URI
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_LIST
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_REMOTE_TRACK_URI
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_TRACK_NAME
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation
import com.tencent.mmkv.MMKV

/**
 * The implementation of the local data store. The responsibility is selecting a correct
 * local service(Database/Local file) to access the data.
 */
class LocalDataStore(
    private val rankingDao: RankingDao,
    private val searchingHistoryDao: SearchingHistoryDao,
    private val listenHistoryDao: ListenHistoryDao,
    private val localMusicDao: LocalMusicDao,
    private val playlistDao: PlaylistDao,
    private val mmkv: MMKV
) : DataStore {
    //region Ranking
    override suspend fun createRankingData(params: List<RankingIdData>) = tryWrapper {
        rankingDao.insert(*params.toTypedArray())
    }

    override suspend fun getRankingData() = rankingDao.retrieveRankings()

    override suspend fun modifyRankingData(parameterable: Parameterable) = tryWrapper {
        val id = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_RANK_ID])
        val uri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_TOP_TRACK_URI])
        val number = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_NUMBER_OF_TRACK])
        rankingDao.replaceBy(id, uri, number)
    }
    //endregion

    //region Search History
    override suspend fun createSearchHistory(parameterable: Parameterable) = tryWrapper {
        val keyword = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_KEYWORD])
        searchingHistoryDao.insertBy(keyword)
    }

    override suspend fun getSearchHistories(parameterable: Parameterable): List<SearchHistoryData> {
        val limit = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_LIMIT])
        return searchingHistoryDao.retrieveHistories(limit)
    }

    override suspend fun removeSearchHistory(parameterable: Parameterable) = tryWrapper {
        val data = castOrNull<SearchHistModel>(parameterable.toApiAnyParam()[PARAM_NAME_KEYWORD_MODEL])
            ?.let(SearchHistoryDMapper()::toDataFrom)

        if (data != null)
            searchingHistoryDao.release(data)
        else {
            val keyword = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_KEYWORD])
            searchingHistoryDao.releaseBy(keyword)
        }
    }
    //endregion

    //region Playlist
    override suspend fun fetchLocalMusics(parameterable: Parameterable) = TODO()

    override suspend fun addLocalMusic(parameterable: Parameterable): Boolean {
        try {
            val trackName = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_TRACK_NAME])
            val artistName = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_ARTIST_NAME])
            val duration = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_DURATION])
            val hasOwn = cast<Boolean>(parameterable.toApiAnyParam()[PARAM_NAME_HAS_OWN])
            val remoteTrackUri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_REMOTE_TRACK_URI])
            val localTrackUri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_LOCAL_TRACK_URI])
            val coverUri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_COVER_URI])
            val playlistList = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_PLAYLIST_LIST])
            localMusicDao.insertBy(LocalMusicData(0,
                                                  trackName,
                                                  artistName,
                                                  duration,
                                                  hasOwn,
                                                  remoteTrackUri,
                                                  localTrackUri,
                                                  coverUri,
                                                  playlistList))
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override suspend fun updateLocalMusic(parameterable: Parameterable) = TODO()

    override suspend fun deleteLocalMusic(parameterable: Parameterable) = TODO()

    override suspend fun fetchPlaylists() = playlistDao.retrievePlaylists()

    override suspend fun fetchPlaylist(parameterable: Parameterable): PlaylistInfoData {
        val id = cast<Int>(parameterable.toApiAnyParam()["id"])
        return playlistDao.retrievePlaylist(id)
    }

    override suspend fun addPlaylist(parameterable: Parameterable) = TODO()

    override suspend fun updatePlaylist(parameterable: Parameterable) = TODO()

    override suspend fun deletePlaylist(parameterable: Parameterable) = TODO()
    //endregion

    //region UnsupportedOperationException
    override suspend fun getMusicRanking(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getMusic(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getHotPlaylist(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getSongList(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getAlbumInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getArtistInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getArtistTopAlbum(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getArtistTopTrack(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getSimilarArtistInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getArtistPhotosInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getTrackInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getSimilarTrackInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getChartTopTrack(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getChartTopArtist(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getChartTopTag(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getTagInfo(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getTagTopAlbum(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getTagTopArtist(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getTagTopTrack(parameterable: Parameterable) =
        UnsupportedOperation()
    //endregion

    private fun tryWrapper(tryBlock: () -> Unit): Boolean {
        try {
            tryBlock()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}
