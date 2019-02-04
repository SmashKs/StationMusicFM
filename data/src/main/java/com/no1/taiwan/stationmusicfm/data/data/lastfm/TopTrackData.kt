package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopTrackData(
    @SerializedName("tracks")
    val track: Tracks
) : Data {
    data class Tracks(
        @SerializedName("track")
        val tracks: List<TrackData.Track>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
