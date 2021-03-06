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

package com.no1.taiwan.stationmusicfm.domain.parameters.musicbank

import com.no1.taiwan.stationmusicfm.domain.parameters.BaseParams
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

data class RankParams(
    private val rankId: Int = DEFAULT_INT,
    private val topTrackUri: String = DEFAULT_STR,
    private val numberOfTrack: Int = DEFAULT_INT
) : BaseParams() {
    companion object {
        const val PARAM_NAME_RANK_ID = "rank_id"
        const val PARAM_NAME_TOP_TRACK_URI = "top track uri"
        const val PARAM_NAME_NUMBER_OF_TRACK = "number of track"
    }

    override fun toApiParam() = hashMapOf(
        PARAM_NAME_RANK_ID to rankId.toString(),
        PARAM_NAME_TOP_TRACK_URI to topTrackUri,
        PARAM_NAME_NUMBER_OF_TRACK to numberOfTrack.toString())

    override fun toApiAnyParam() = hashMapOf(
        PARAM_NAME_RANK_ID to rankId,
        PARAM_NAME_TOP_TRACK_URI to topTrackUri,
        PARAM_NAME_NUMBER_OF_TRACK to numberOfTrack)
}
