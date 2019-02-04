package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class ArtistTopAlbumInfoData(
    @SerializedName("topalbums")
    val topAlbums: TopAlbumsData
) : Data {
    data class TopAlbumsData(
        @SerializedName("album")
        val albums: List<AlbumInfoData.AlbumWithPlaycountData>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
