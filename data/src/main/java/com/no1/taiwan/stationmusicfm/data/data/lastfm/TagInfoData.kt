package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.no1.taiwan.stationmusicfm.data.data.Data

data class TagInfoData(
    val tag: TagData
) : Data {
    data class TagData(
        val name: String?,
        val total: Int?,
        val reach: Int?,
        val url: String?,
        val wiki: CommonLastFmData.WikiData?
    ) : Data
}
