package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class Tags(
    @SerializedName("tag")
    val tags: List<TagData.Tag>?
) : Data

data class Image(
    @SerializedName("#text")
    val text: String?,
    val size: String?
) : Data

data class Streamable(
    @SerializedName("#text")
    val text: String?,
    val fulltrack: String?
) : Data

data class Wiki(
    val published: String?,
    val summary: String?,
    val content: String?
) : Data

data class Attr(
    val artist: String?,
    val totalPages: String?,
    val total: String?,
    val rank: String?,
    val position: String?,
    val perPage: String?,
    val page: String?
) : Data
