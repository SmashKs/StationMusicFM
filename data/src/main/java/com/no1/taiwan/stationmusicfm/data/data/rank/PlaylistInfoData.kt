package com.no1.taiwan.stationmusicfm.data.data.rank

import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class PlaylistInfoData(
    val status: String = DEFAULT_STR,
    val data: CommonMusicData.PlayListData = CommonMusicData.PlayListData()
) : Data
