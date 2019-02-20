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

import com.no1.taiwan.stationmusicfm.domain.parameters.BaseParams
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.ext.takeUnlessDefault

data class TagParams(
    val tagName: String = DEFAULT_STR,
    val language: String = DEFAULT_STR
) : BaseParams() {
    companion object {
        const val PARAM_NAME_TAG = "tag"
        const val PARAM_NAME_LANG = "lang"
    }

    override fun toApiParam() = super.toApiParam().apply {
        put(PARAM_NAME_TAG, tagName)
        language.takeUnlessDefault { put(PARAM_NAME_LANG, it) }
    }

    override fun toApiAnyParam() = super.toApiAnyParam().apply {
        put(PARAM_NAME_TAG, tagName)
        language.takeUnlessDefault { put(PARAM_NAME_LANG, it) }
    }
}
