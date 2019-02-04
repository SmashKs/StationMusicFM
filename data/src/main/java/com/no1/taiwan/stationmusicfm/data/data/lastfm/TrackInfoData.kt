package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TrackInfoData(
    val track: TrackData?
) : Data {
    data class TrackData(
        val streamable: CommonLastFmData.StreamableData?
    ) : BaseTrackData(), Data

    data class TrackWithStreamableStringData(
        val streamable: String?
    ) : BaseTrackData(), Data

    open class BaseTrackData(
        val album: AlbumInfoData.AlbumData? = null,
        @SerializedName("@attr")
        val attr: CommonLastFmData.AttrData? = null,
        val artist: ArtistInfoData.ArtistData? = null,
        val duration: String? = null,
        @SerializedName("image")
        val images: List<CommonLastFmData.ImageData>? = null,
        val listeners: String? = null,
        val match: Double? = null,
        val mbid: String? = null,
        val name: String? = null,
        @SerializedName("playcount")
        val playcount: String? = null,
        @SerializedName("toptags")
        val topTag: CommonLastFmData.TagsData? = null,
        val url: String? = null,
        val realUrl: String? = null,
        val wiki: CommonLastFmData.WikiData? = null
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
