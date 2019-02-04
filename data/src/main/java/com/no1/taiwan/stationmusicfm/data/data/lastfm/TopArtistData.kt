package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopArtistData(
    val artists: Artists
) : Data {
    data class Artists(
        @SerializedName("artist")
        val artists: List<ArtistData.Artist>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
