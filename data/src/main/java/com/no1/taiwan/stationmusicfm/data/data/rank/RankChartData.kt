package com.no1.taiwan.stationmusicfm.data.data.rank

import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class RankChartData(
    var id: Long = 0,
    var rankType: Int = 0,
    var coverUrl: String = DEFAULT_STR,
    var chartName: String = DEFAULT_STR,
    var updateTime: String = DEFAULT_STR
) : Data
