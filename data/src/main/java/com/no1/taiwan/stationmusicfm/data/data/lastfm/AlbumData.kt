package com.no1.taiwan.stationmusicfm.data.data.lastfm

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class AlbumData(
    val album: Album?
) : Data {
    data class Album(
        val artist: String?,
        @SerializedName("playcount")
        val playCount: String? = null
    ) : BaseAlbum(), Data

    data class AlbumWithArtist(
        val artist: ArtistData.Artist?,
        @SerializedName("playcount")
        val playCount: String? = null
    ) : BaseAlbum(), Data

    data class AlbumWithPlaycount(
        val artist: ArtistData.Artist?,
        @SerializedName("playcount")
        val playCount: Int?,
        val index: Int = -1
    ) : BaseAlbum(), Data

    open class BaseAlbum(
        @SerializedName("@attr")
        val attr: Attr? = null,
        @SerializedName("image")
        val images: List<Image>? = null,
        val listeners: String? = null,
        val mbid: String? = null,
        val name: String? = null,
        val tags: Tags? = null,
        val title: String? = null,
        @SerializedName("tracks")
        val track: TopTrackData.Tracks? = null,
        val url: String? = null,
        val wiki: Wiki? = null
    ) : Data
}
