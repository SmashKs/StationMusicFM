package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

object CommonLastFmData {
    data class TagsData(
        @SerializedName("tag")
        val tags: List<TagInfoData.TagData>?,
        @SerializedName("@attr")
        val attr: AttrData?
    ) : Data

    data class ImageData(
        @SerializedName("#text")
        val text: String?,
        val size: String?
    ) : Data

    data class StreamableData(
        @SerializedName("#text")
        val text: String?,
        val fulltrack: String?
    ) : Data

    data class WikiData(
        val published: String?,
        val summary: String?,
        val content: String?
    ) : Data

    data class AttrData(
        val artist: String?,
        val totalPages: String?,
        val total: String?,
        val rank: String?,
        val position: String?,
        val perPage: String?,
        val page: String?
    ) : Data
}
