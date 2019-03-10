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

import android.content.Context
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.data.BuildConfig
import com.no1.taiwan.stationmusicfm.data.R
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.data.remote.Constants
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmExtraService
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService
import com.no1.taiwan.stationmusicfm.data.remote.services.MusicBankService
import com.no1.taiwan.stationmusicfm.data.remote.services.SeekerBankService
import com.no1.taiwan.stationmusicfm.domain.Fields
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.ArtistParams
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.BaseWithPagingParams
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation

/**
 * The implementation of the remote data store. The responsibility is selecting a correct
 * remote service to access the data.
 */
class RemoteDataStore(
    private val lastFmService: LastFmService,
    private val lastFmExtraService: LastFmExtraService,
    private val musicBankService: MusicBankService,
    private val seekerBankService: SeekerBankService,
    context: Context
) : DataStore {
    private val lastFmToken by lazy { context.getString(R.string.lastfm_api_key) }
    private val lastFmAuth by lazy {
        hashMapOf(Constants.LASTFM_QUERY_TOKEN to lastFmToken)
    }

    //region 🔽 Music Bank
    override suspend fun getMusicRanking(parameterable: Parameterable) =
        musicBankService.retrieveMusicRanking(parameterable.toApiParam()).await()

    override suspend fun getMusic(parameterable: Parameterable): MusicInfoData {
        val queries = parameterable.toApiParam().apply {
            put(BuildConfig.query1, BuildConfig.param1)
            put(BuildConfig.query2, BuildConfig.param2)
        }

        return seekerBankService.retrieveSearchMusic(queries).await()
    }

    override suspend fun getHotPlaylist(parameterable: Parameterable) =
        musicBankService.retrieveHotPlaylist(parameterable.toApiParam()).await()

    override suspend fun getSongList(parameterable: Parameterable) =
        musicBankService.retrieveSongList(parameterable.toApiParam()).await()
    //endregion

    //region 🔽 Last Fm
    override suspend fun getAlbumInfo(parameterable: Parameterable) =
        lastFmService.retrieveAlbumInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                           Constants.LASTFM_PARAM_ALBUM_GET_INFO)).await()

    override suspend fun getArtistInfo(parameterable: Parameterable) =
        lastFmService.retrieveArtistInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                            Constants.LASTFM_PARAM_ARTIST_GET_INFO)).await()

    override suspend fun getArtistTopAlbum(parameterable: Parameterable) =
        lastFmService.retrieveArtistTopAlbum(combineLastFmQuery(parameterable.toApiParam(),
                                                                Constants.LASTFM_PARAM_ARTIST_GET_TOP_ALBUMS)).await()

    override suspend fun getArtistTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveArtistTopTrack(combineLastFmQuery(parameterable.toApiParam(),
                                                                Constants.LASTFM_PARAM_ARTIST_GET_TOP_TRACKS)).await()

    override suspend fun getSimilarArtistInfo(parameterable: Parameterable) =
        lastFmService.retrieveSimilarArtistInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                                   Constants.LASTFM_PARAM_ARTIST_GET_SIMILAR)).await()

    override suspend fun getArtistPhotosInfo(parameterable: Parameterable) = let {
        val artistName = cast<String>(parameterable.toApiAnyParam()[ArtistParams.PARAM_NAME_ARTIST])
        val page = cast<Int>(parameterable.toApiAnyParam()[BaseWithPagingParams.PARAM_NAME_PAGE])
        lastFmExtraService.retrieveArtistPhotosInfo(artistName, page).await()
    }

    override suspend fun getTrackInfo(parameterable: Parameterable) =
        lastFmService.retrieveTrackInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                           Constants.LASTFM_PARAM_TRACK_INFO)).await()

    override suspend fun getSimilarTrackInfo(parameterable: Parameterable) =
        lastFmService.retrieveSimilarTrackInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                                  Constants.LASTFM_PARAM_TRACK_GET_SIMILAR)).await()

    override suspend fun getChartTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveChartTopTrack(combineLastFmQuery(parameterable.toApiParam(),
                                                               Constants.LASTFM_PARAM_CHART_GET_TOP_TRACKS)).await()

    override suspend fun getChartTopArtist(parameterable: Parameterable) =
        lastFmService.retrieveChartTopArtist(combineLastFmQuery(parameterable.toApiParam(),
                                                                Constants.LASTFM_PARAM_CHART_GET_TOP_ARTISTS)).await()

    override suspend fun getChartTopTag(parameterable: Parameterable) =
        lastFmService.retrieveChartTopTag(combineLastFmQuery(parameterable.toApiParam(),
                                                             Constants.LASTFM_PARAM_CHART_GET_TOP_TAGS)).await()

    override suspend fun getTagInfo(parameterable: Parameterable) =
        lastFmService.retrieveTagInfo(combineLastFmQuery(parameterable.toApiParam(),
                                                         Constants.LASTFM_PARAM_TAG_GET_INFO)).await()

    override suspend fun getTagTopAlbum(parameterable: Parameterable) =
        lastFmService.retrieveTagTopAlbum(combineLastFmQuery(parameterable.toApiParam(),
                                                             Constants.LASTFM_PARAM_TAG_GET_TOP_ALBUMS)).await()

    override suspend fun getTagTopArtist(parameterable: Parameterable) =
        lastFmService.retrieveTagTopArtist(combineLastFmQuery(parameterable.toApiParam(),
                                                              Constants.LASTFM_PARAM_TAG_GET_TOP_ARTISTS)).await()

    override suspend fun getTagTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveTagTopTrack(combineLastFmQuery(parameterable.toApiParam(),
                                                             Constants.LASTFM_PARAM_TAG_GET_TOP_TRACKS)).await()
    //endregion

    private fun combineLastFmQuery(query: Fields, method: String) = query.apply {
        putAll(lastFmAuth)
        put(Constants.LASTFM_QUERY_METHOD, method)
    }

    //region UnsupportedOperationException
    override suspend fun createRankingData(params: List<RankingIdData>) =
        UnsupportedOperation()

    override suspend fun getRankingData(): List<RankingIdData> =
        UnsupportedOperation()

    override suspend fun modifyRankingData(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun createSearchHistory(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun getSearchHistories(parameterable: Parameterable) =
        UnsupportedOperation()

    override suspend fun removeSearchHistory(parameterable: Parameterable) =
        UnsupportedOperation()
    //endregion
}
