package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopTagData(
    @SerializedName("tags")
    val tag: Tags
) : Data {
    data class Tags(
        @SerializedName("tag")
        val tags: List<TagData.Tag>,
        @SerializedName("@attr")
        val attr: Attr?
    ) : Data
}
