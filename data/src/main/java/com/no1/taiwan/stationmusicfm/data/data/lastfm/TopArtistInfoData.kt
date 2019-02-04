package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopArtistInfoData(
    val artists: ArtistsData
) : Data {
    data class ArtistsData(
        @SerializedName("artist")
        val artists: List<ArtistInfoData.ArtistData>,
        @SerializedName("@attr")
        val attr: CommonLastFmData.AttrData?
    ) : Data
}
