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

package com.no1.taiwan.stationmusicfm.data.datastores

import com.no1.taiwan.stationmusicfm.data.BuildConfig
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService
import com.no1.taiwan.stationmusicfm.data.remote.services.MusicBankService
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable

/**
 * The implementation of the remote data store. The responsibility is selecting a correct
 * remote service to access the data.
 */
class RemoteDataStore(
    private val lastFmService: LastFmService,
    private val musicBankService: MusicBankService
) : DataStore {
    override suspend fun getMusicRanking(parameterable: Parameterable) =
        musicBankService.retrieveMusicRanking(parameterable.toApiParam()).await()

    override suspend fun getMusic(parameterable: Parameterable): MusicInfoData {
        val queries = parameterable.toApiParam().apply {
            put(BuildConfig.query1, BuildConfig.param1)
            put(BuildConfig.query2, BuildConfig.param2)
        }

        return musicBankService.retrieveSearchMusic(queries).await()
    }

    override suspend fun getHotPlaylist(parameterable: Parameterable) =
        musicBankService.retrieveHotPlaylist(parameterable.toApiParam()).await()

    override suspend fun getSongList(parameterable: Parameterable) =
        musicBankService.retrieveSongList(parameterable.toApiParam()).await()

    override suspend fun getAlbumInfo(parameterable: Parameterable) =
        lastFmService.retrieveAlbumInfo(parameterable.toApiParam()).await()

    override suspend fun getArtistInfo(parameterable: Parameterable) =
        lastFmService.retrieveArtistInfo(parameterable.toApiParam()).await()

    override suspend fun getArtistTopAlbum(parameterable: Parameterable) =
        lastFmService.retrieveArtistTopAlbum(parameterable.toApiParam()).await()

    override suspend fun getArtistTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveArtistTopTrack(parameterable.toApiParam()).await()

    override suspend fun getSimilarArtistInfo(parameterable: Parameterable) =
        lastFmService.retrieveSimilarArtistInfo(parameterable.toApiParam()).await()

    override suspend fun getTrackInfo(parameterable: Parameterable) =
        lastFmService.retrieveTrackInfo(parameterable.toApiParam()).await()

    override suspend fun getSimilarTrackInfo(parameterable: Parameterable) =
        lastFmService.retrieveSimilarTrackInfo(parameterable.toApiParam()).await()

    override suspend fun getChartTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveChartTopTrack(parameterable.toApiParam()).await()

    override suspend fun getChartTopArtist(parameterable: Parameterable) =
        lastFmService.retrieveChartTopArtist(parameterable.toApiParam()).await()

    override suspend fun getChartTopTag(parameterable: Parameterable) =
        lastFmService.retrieveChartTopTag(parameterable.toApiParam()).await()

    override suspend fun getTagInfo(parameterable: Parameterable) =
        lastFmService.retrieveTagInfo(parameterable.toApiParam()).await()

    override suspend fun getTagTopAlbum(parameterable: Parameterable) =
        lastFmService.retrieveTagTopAlbum(parameterable.toApiParam()).await()

    override suspend fun getTagTopArtist(parameterable: Parameterable) =
        lastFmService.retrieveTagTopArtist(parameterable.toApiParam()).await()

    override suspend fun getTagTopTrack(parameterable: Parameterable) =
        lastFmService.retrieveTagTopTrack(parameterable.toApiParam()).await()
}
