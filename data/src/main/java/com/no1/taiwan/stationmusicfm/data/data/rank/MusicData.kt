package com.no1.taiwan.stationmusicfm.data.data.rank

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class MusicData(
    val status: String = DEFAULT_STR,
    val data: MusicInfoData = MusicInfoData()
) : Data {
    data class MusicInfoData(
        // 🔽 Only Music has.
        @SerializedName("has_more")
        val hasMore: Boolean = false,
        val items: List<CommonMusicData.SongData> = emptyList(),
        // 🔽 Only Rank has.
        val timestamp: Double = 0.0,
        val songs: List<CommonMusicData.SongData> = emptyList()
    ) : Data
}
