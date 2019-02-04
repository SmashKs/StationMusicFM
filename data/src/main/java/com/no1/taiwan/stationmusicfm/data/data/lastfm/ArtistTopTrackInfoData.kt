package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class ArtistTopTrackInfoData(
    @SerializedName("toptracks")
    val topTracks: TracksData
) : Data {
    data class TracksData(
        @SerializedName("track")
        val tracks: List<TrackInfoData.TrackWithStreamableStringData>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
