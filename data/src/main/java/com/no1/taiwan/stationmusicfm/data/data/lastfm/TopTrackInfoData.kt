package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopTrackInfoData(
    @SerializedName("tracks")
    val track: TracksData
) : Data {
    data class TracksData(
        @SerializedName("track")
        val tracks: List<TrackInfoData.TrackData>,
        @SerializedName("@attr")
        val attr: CommonLastFmData.AttrData?
    ) : Data
}
