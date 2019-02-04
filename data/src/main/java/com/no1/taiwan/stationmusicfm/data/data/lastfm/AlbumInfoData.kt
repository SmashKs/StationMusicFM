package com.no1.taiwan.stationmusicfm.data.data.lastfm

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import org.w3c.dom.Attr

data class AlbumInfoData(
    val album: AlbumData?
) : Data {
    data class AlbumData(
        val artist: String?,
        @SerializedName("playcount")
        val playCount: String? = null
    ) : BaseAlbumData(), Data

    data class AlbumWithArtistData(
        val artist: ArtistInfoData.ArtistData?,
        @SerializedName("playcount")
        val playCount: String? = null
    ) : BaseAlbumData(), Data

    data class AlbumWithPlaycountData(
        val artist: ArtistInfoData.ArtistData?,
        @SerializedName("playcount")
        val playCount: Int?,
        val index: Int = -1
    ) : BaseAlbumData(), Data

    open class BaseAlbumData(
        @SerializedName("@attr")
        val attr: Attr? = null,
        @SerializedName("image")
        val images: List<Image>? = null,
        val listeners: String? = null,
        val mbid: String? = null,
        val name: String? = null,
        val tags: CommonLastFmData.TagsData? = null,
        val title: String? = null,
        @SerializedName("tracks")
        val track: TopTrackInfoData.TracksData? = null,
        val url: String? = null,
        val wiki: CommonLastFmData.WikiData? = null
    ) : Data
}
