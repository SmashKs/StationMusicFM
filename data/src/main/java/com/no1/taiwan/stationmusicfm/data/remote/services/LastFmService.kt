package com.no1.taiwan.stationmusicfm.data.remote.services

import com.no1.taiwan.stationmusicfm.data.data.lastfm.AlbumData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistSimilarData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistTopAlbumData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistTopTrackData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TagData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TagTopArtistData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopAlbumData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopArtistData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTagData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TopTrackData
import com.no1.taiwan.stationmusicfm.data.data.lastfm.TrackData
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
    //region Album
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveAlbumInfo(@QueryMap queries: Map<String, String>): Deferred<AlbumData>
    //endregion

    //region Artist
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistInfo(@QueryMap queries: Map<String, String>): Deferred<ArtistData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistTopAlbum(@QueryMap queries: Map<String, String>): Deferred<ArtistTopAlbumData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveArtistTopTrack(@QueryMap queries: Map<String, String>): Deferred<ArtistTopTrackData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveSimilarArtistInfo(@QueryMap queries: Map<String, String>): Deferred<ArtistSimilarData>
    //endregion

    //region Track
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTrackInfo(@QueryMap queries: Map<String, String>): Deferred<TrackData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveSimilarTrackInfo(@QueryMap queries: Map<String, String>): Deferred<TrackSimilarData>
    //endregion

    //region Chart
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopTrack(@QueryMap queries: Map<String, String>): Deferred<TopTrackData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopArtist(@QueryMap queries: Map<String, String>): Deferred<TopArtistData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveChartTopTag(@QueryMap queries: Map<String, String>): Deferred<TopTagData>
    //endregion

    //region Tag
    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagInfo(@QueryMap queries: Map<String, String>): Deferred<TagData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopAlbum(@QueryMap queries: Map<String, String>): Deferred<TopAlbumData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopArtist(@QueryMap queries: Map<String, String>): Deferred<TagTopArtistData>

    @GET(LastFmConfig.API_REQUEST)
    fun retrieveTagTopTrack(@QueryMap queries: Map<String, String>): Deferred<TopTrackData>
    //endregion
}
