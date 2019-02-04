package com.no1.taiwan.stationmusicfm.data.data.lastfm

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data

data class TopTagInfoData(
    @SerializedName("tags")
    val tag: CommonLastFmData.TagsData
) : Data
