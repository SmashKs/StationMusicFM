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
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Thru [retrofit2.Retrofit] we can just define the interfaces which we want to access for.
 * Using prefix name (retrieve), (insert), (replace), (release)
 */
interface LastFmService {
    //region AlbumData
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveAlbumInfo(@QueryMap queries: Map<String, String>): Deferred<AlbumInfoData>
    //endregion

    //region ArtistData
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistInfo(@QueryMap queries: Map<String, String>): Deferred<ArtistInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistTopAlbum(@QueryMap queries: Map<String, String>): Deferred<ArtistTopAlbumInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistTopTrack(@QueryMap queries: Map<String, String>): Deferred<ArtistTopTrackInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveSimilarArtistInfo(@QueryMap queries: Map<String, String>): Deferred<ArtistSimilarData>
    //endregion

    //region TrackData
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTrackInfo(@QueryMap queries: Map<String, String>): Deferred<TrackInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveSimilarTrackInfo(@QueryMap queries: Map<String, String>): Deferred<TrackSimilarData>
    //endregion

    //region Chart
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopTrack(@QueryMap queries: Map<String, String>): Deferred<TopTrackInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopArtist(@QueryMap queries: Map<String, String>): Deferred<TopArtistInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopTag(@QueryMap queries: Map<String, String>): Deferred<TopTagInfoData>
    //endregion

    //region TagData
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagInfo(@QueryMap queries: Map<String, String>): Deferred<TagInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopAlbum(@QueryMap queries: Map<String, String>): Deferred<TopAlbumInfoData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopArtist(@QueryMap queries: Map<String, String>): Deferred<TagTopArtistData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopTrack(@QueryMap queries: Map<String, String>): Deferred<TopTrackInfoData>
    //endregion
}
