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
import com.no1.taiwan.stationmusicfm.data.BuildConfig
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
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_ADD_OR_MINUS
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_ID
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_PLAYLIST_LIST
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_REMOTE_TRACK_URI
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.LocalMusicParams.Companion.PARAM_NAME_TRACK_NAME
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams.Companion.PARAM_NAME_ADD_OR_MINUS
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams.Companion.PARAM_NAME_IDS
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams.Companion.PARAM_NAME_NAMES
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams.Companion.PARAM_NAME_TRACK_COUNT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation

/**
 * The implementation of the local data store. The responsibility is selecting a correct
 * local service(Database/Local file) to access the data.
 */
class LocalDataStore(
    private val rankingDao: RankingDao,
    private val searchingHistoryDao: SearchingHistoryDao,
    private val listenHistoryDao: ListenHistoryDao,
    private val localMusicDao: LocalMusicDao,
    private val playlistDao: PlaylistDao
    // private val mmkv: MMKV
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
    override suspend fun createOrModifySearchHistory(parameterable: Parameterable) = tryWrapper {
        val keyword = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_KEYWORD])
        searchingHistoryDao.insertBy(keyword)
    }

    override suspend fun getSearchHistories(parameterable: Parameterable): List<SearchHistoryData> {
        val limit = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_LIMIT])
        return searchingHistoryDao.retrieveHistories(limit)
    }

    override suspend fun removeSearchHistory(parameterable: Parameterable) = tryWrapper {
        val data =
            castOrNull<SearchHistModel>(parameterable.toApiAnyParam()[PARAM_NAME_KEYWORD_MODEL])
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
    override suspend fun getLocalMusics(parameterable: Parameterable) =
        localMusicDao.retrieveMusics()

    override suspend fun createOrModifyLocalMusic(parameterable: Parameterable) = tryWrapper {
        val trackName = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_TRACK_NAME])
        val artistName = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_ARTIST_NAME])
        val duration = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_DURATION])
        val hasOwn = cast<Boolean>(parameterable.toApiAnyParam()[PARAM_NAME_HAS_OWN])
        val remoteTrackUri =
            cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_REMOTE_TRACK_URI])
        val localTrackUri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_LOCAL_TRACK_URI])
        val coverUri = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_COVER_URI])
        val playlistList = cast<String>(parameterable.toApiAnyParam()[PARAM_NAME_PLAYLIST_LIST])
        val addOrMinus =
            cast<Boolean>(parameterable.toApiAnyParam()[PARAM_NAME_PLAYLIST_ADD_OR_MINUS])
        localMusicDao.insertBy(LocalMusicData(0,
                                              trackName,
                                              artistName,
                                              duration,
                                              hasOwn,
                                              remoteTrackUri,
                                              localTrackUri,
                                              coverUri,
                                              playlistList), addOrMinus)
    }

    override suspend fun removeLocalMusic(parameterable: Parameterable) = tryWrapper {
        val id = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_PLAYLIST_ID])
        localMusicDao.releaseBy(id)
    }

    override suspend fun getPlaylists() = playlistDao.retrievePlaylists()

    override suspend fun getPlaylist(parameterable: Parameterable): PlaylistInfoData {
        val id = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_PLAYLIST_ID])
        return playlistDao.retrievePlaylist(id)
    }

    override suspend fun getTheNewestPlaylist() = playlistDao.retrieveLatestPlaylist()

    override suspend fun createPlaylist(parameterable: Parameterable) = tryWrapper {
        val ids = cast<List<Int>>(parameterable.toApiAnyParam()[PARAM_NAME_IDS])
        val names = cast<List<String>>(parameterable.toApiAnyParam()[PARAM_NAME_NAMES])
        val playlists = ids.zip(names)
            .map { (id, name) -> PlaylistInfoData(id, name) }
            .toTypedArray()
        playlistDao.insert(*playlists)
    }

    override suspend fun modifyPlaylist(parameterable: Parameterable) = tryWrapper {
        val id = cast<List<Int>>(parameterable.toApiAnyParam()[PARAM_NAME_IDS]).first()
        val name = cast<List<String>>(parameterable.toApiAnyParam()[PARAM_NAME_NAMES]).first()
        val trackNumbers =
            castOrNull<Int>(parameterable.toApiAnyParam()[PARAM_NAME_TRACK_COUNT]) ?: DEFAULT_INT
        playlistDao.replaceBy(id, name, trackNumbers)
    }

    override suspend fun modifyCountOfPlaylist(parameterable: Parameterable) = tryWrapper {
        val id = cast<List<Int>>(parameterable.toApiAnyParam()[PARAM_NAME_IDS]).first()
        val addOrMinus = cast<Boolean>(parameterable.toApiAnyParam()[PARAM_NAME_ADD_OR_MINUS])
        playlistDao.apply { if (addOrMinus) increaseCountBy(id) else decreaseCountBy(id) }
    }

    override suspend fun removePlaylist(parameterable: Parameterable) = tryWrapper {
        val id = cast<List<Int>>(parameterable.toApiAnyParam()[PARAM_NAME_IDS]).first()
        playlistDao.releaseBy(id)
    }

    override suspend fun getListenedHistories(parameterable: Parameterable): List<LocalMusicData> {
        val limit = cast<Int>(parameterable.toApiAnyParam()[PARAM_NAME_LIMIT])
        return listenHistoryDao.retrieveHistories(limit)
    }

    override suspend fun getTypeOfHistories(parameterable: Parameterable): List<LocalMusicData> {
        val ids = cast<List<Int>>(parameterable.toApiAnyParam()[PARAM_NAME_IDS])
        if (ids.first() == PlaylistIndex.DOWNLOADED.ordinal) {
            return listenHistoryDao.retrieveDownloadedMusics()
        }
        return listenHistoryDao.retrieveTypeOfMusics(ids.first())
    }
    //endregion

    //region UnsupportedOperationException
    override suspend fun getMusicRanking(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getMusicRanks(parameterable: Parameterable) =
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
            if (BuildConfig.DEBUG)
                e.printStackTrace()
            return false
        }
        return true
    }
}
