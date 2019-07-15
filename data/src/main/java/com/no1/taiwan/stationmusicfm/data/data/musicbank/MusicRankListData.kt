/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.no1.taiwan.stationmusicfm.data.data.musicbank

import com.google.gson.annotations.SerializedName
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_DOUBLE
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class MusicRankListData(
    @SerializedName("status")
    val status: String = DEFAULT_STR,
    @SerializedName("data")
    val briefRankDatas: List<BriefRankData> = emptyList()
) : Data {
    data class BriefRankData(
        @SerializedName("title")
        val title: String = DEFAULT_STR,
        @SerializedName("timestamp")
        val timestamp: Double = DEFAULT_DOUBLE,
        @SerializedName("sub_title")
        val subTitle: String = DEFAULT_STR,
        @SerializedName("cover_url")
        val coverUrl: String = DEFAULT_STR,
        @SerializedName("source_tip")
        val sourceTip: String = DEFAULT_STR,
        @SerializedName("type")
        val type: Int = DEFAULT_INT,
        @SerializedName("rank_id")
        val rankId: Int = DEFAULT_INT
    ) : Data
}
