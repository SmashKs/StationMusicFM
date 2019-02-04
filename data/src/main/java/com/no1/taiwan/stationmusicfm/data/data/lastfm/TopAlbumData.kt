package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopAlbumData(
    val albums: TopAlbums
) : Data {
    data class TopAlbums(
        @SerializedName("album")
        val albums: List<AlbumData.AlbumWithArtist>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
