package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TrackData(
    val track: Track?
) : Data {
    data class Track(
        val streamable: Streamable?
    ) : BaseTrack(), Data

    data class TrackWithStreamableString(
        val streamable: String?
    ) : BaseTrack(), Data

    open class BaseTrack(
        val album: AlbumData.Album? = null,
        @SerializedName("@attr")
        val attr: Attr? = null,
        val artist: ArtistData.Artist? = null,
        val duration: String? = null,
        @SerializedName("image")
        val images: List<Image>? = null,
        val listeners: String? = null,
        val match: Double? = null,
        val mbid: String? = null,
        val name: String? = null,
        @SerializedName("playcount")
        val playcount: String? = null,
        @SerializedName("toptags")
        val topTag: Tags? = null,
        val url: String? = null,
        val realUrl: String? = null,
        val wiki: Wiki? = null
    ) : Data {
        override fun toString() =
            """
album: $album
attr: $attr
artist: $artist
duration: $duration
images: $images
listeners: $listeners
match: $match
mbid: $mbid
name: $name
playcount: $playcount
topTag: $topTag
url: $url
realUrl: $realUrl
wiki: $wiki
"""
    }
}
