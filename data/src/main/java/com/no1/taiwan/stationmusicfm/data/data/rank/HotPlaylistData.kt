package com.no1.taiwan.stationmusicfm.data.data.rank

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class HotPlaylistData(
    val status: String = DEFAULT_STR,
    val data: HotListInfoData = HotListInfoData()
) : Data {
    data class HotListInfoData(
        @SerializedName("has_more")
        val hasMore: Int = 0,
        @SerializedName("song_lists")
        val playlists: List<CommonMusicData.PlayListData> = emptyList()
    ) : Data
}
