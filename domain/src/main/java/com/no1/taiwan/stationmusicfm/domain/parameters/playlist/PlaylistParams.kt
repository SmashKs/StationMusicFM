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

package com.no1.taiwan.stationmusicfm.domain.parameters.playlist

import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation

data class PlaylistParams(
    private val ids: List<Int> = emptyList(),
    private val names: List<String> = emptyList(),
    private val addOrMinus: Boolean = true,  // True → add; False → minus
    private val trackCount: Int = DEFAULT_INT
) : Parameterable {
    companion object {
        const val PARAM_NAME_IDS = "playlist id list"
        const val PARAM_NAME_NAMES = "playlist name list"
        const val PARAM_NAME_ADD_OR_MINUS = "playlist increase or decrease track count"
        const val PARAM_NAME_TRACK_COUNT = "playlist track count"
    }

    override fun toApiParam() = UnsupportedOperation()

    override fun toApiAnyParam() = hashMapOf(PARAM_NAME_IDS to ids,
                                             PARAM_NAME_NAMES to names,
                                             PARAM_NAME_ADD_OR_MINUS to addOrMinus,
                                             PARAM_NAME_TRACK_COUNT to trackCount)
}

