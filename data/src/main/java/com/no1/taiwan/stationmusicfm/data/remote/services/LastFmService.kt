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

package com.no1.taiwan.stationmusicfm.data.remote.services

import com.no1.taiwan.stationmusicfm.data.data.lastfm.AlbumInfoData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistInfoData
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
import com.no1.taiwan.stationmusicfm.data.remote.config.LastFmConfig
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Thru [retrofit2.Retrofit] we can just define the interfaces which we want to access for.
 * Using prefix name (retrieve), (insert), (replace), (release)
 */
interface LastFmService {
    //region AlbumData
    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveAlbumInfo(@QueryMap queries: Map<String, String>): AlbumInfoData
    //endregion

    //region ArtistData
    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveArtistInfo(@QueryMap queries: Map<String, String>): ArtistInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveArtistTopAlbum(@QueryMap queries: Map<String, String>): ArtistTopAlbumInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveArtistTopTrack(@QueryMap queries: Map<String, String>): ArtistTopTrackInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveSimilarArtistInfo(@QueryMap queries: Map<String, String>): ArtistSimilarData
    //endregion

    //region TrackData
    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveTrackInfo(@QueryMap queries: Map<String, String>): TrackInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveSimilarTrackInfo(@QueryMap queries: Map<String, String>): TrackSimilarData
    //endregion

    //region Chart
    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveChartTopTrack(@QueryMap queries: Map<String, String>): TopTrackInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveChartTopArtist(@QueryMap queries: Map<String, String>): TopArtistInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveChartTopTag(@QueryMap queries: Map<String, String>): TopTagInfoData
    //endregion

    //region TagData
    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveTagInfo(@QueryMap queries: Map<String, String>): TagInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveTagTopAlbum(@QueryMap queries: Map<String, String>): TopAlbumInfoData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveTagTopArtist(@QueryMap queries: Map<String, String>): TagTopArtistData

    @GET(LastFmConfig.API_REQUEST)
    suspend fun retrieveTagTopTrack(@QueryMap queries: Map<String, String>): TopTrackInfoData
    //endregion
}
