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

package com.no1.taiwan.stationmusicfm.domain.parameters.lastfm

import com.devrapid.kotlinshaver.toInt
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.ext.takeIfDefault
import com.no1.taiwan.stationmusicfm.ext.takeUnlessDefault

data class TrackParams(
    override var mbid: String = DEFAULT_STR,
    val trackName: String = DEFAULT_STR,
    val artistName: String = DEFAULT_STR,
    val userName: String = DEFAULT_STR,
    val autoCorrect: Boolean = true
) : BaseWithPagingParams() {
    companion object {
        const val PARAM_NAME_TRACK = "track"
        const val PARAM_NAME_ARTIST = "artist"
        const val PARAM_NAME_USER_NAME = "username"
        const val PARAM_NAME_AUTO_CORRECT = "autocorrect"
    }

    override fun toApiParam() = super.toApiParam().apply {
        mbid.takeIfDefault {
            put(PARAM_NAME_TRACK, trackName)
            artistName.takeUnlessDefault { put(PARAM_NAME_ARTIST, it) }
            userName.takeUnlessDefault { put(PARAM_NAME_USER_NAME, it) }
            put(PARAM_NAME_AUTO_CORRECT, autoCorrect.toInt().toString())
        }
    }

    override fun toApiAnyParam() = super.toApiAnyParam().apply {
        mbid.takeIfDefault {
            put(PARAM_NAME_TRACK, trackName)
            artistName.takeUnlessDefault { put(PARAM_NAME_ARTIST, it) }
            userName.takeUnlessDefault { put(PARAM_NAME_USER_NAME, it) }
            put(PARAM_NAME_AUTO_CORRECT, autoCorrect)
        }
    }
}
