package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class ArtistTopTrackData(
    @SerializedName("toptracks")
    val topTracks: Tracks
) : Data {
    data class Tracks(
        @SerializedName("track")
        val tracks: List<TrackData.TrackWithStreamableString>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
