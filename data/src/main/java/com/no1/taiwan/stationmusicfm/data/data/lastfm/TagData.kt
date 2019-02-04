package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.no1.taiwan.stationmusicfm.data.data.Data

data class TagData(
    val tag: Tag
) : Data {
    data class Tag(
        val name: String?,
        val total: Int?,
        val reach: Int?,
        val url: String?,
        val wiki: Wiki?
    ) : Data
}
