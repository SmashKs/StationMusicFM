package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopAlbumInfoData(
    val albums: TopAlbumsData
) : Data {
    data class TopAlbumsData(
        @SerializedName("album")
        val albums: List<AlbumInfoData.AlbumWithArtistData>,
        @SerializedName("@attr")
        val attr: CommonLastFmData.AttrData?
    ) : Data
}
