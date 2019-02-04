package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class ArtistTopAlbumData(
    @SerializedName("topalbums")
    val topAlbums: TopAlbums
) : Data {
    data class TopAlbums(
        @SerializedName("album")
        val albums: List<AlbumData.AlbumWithPlaycount>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
