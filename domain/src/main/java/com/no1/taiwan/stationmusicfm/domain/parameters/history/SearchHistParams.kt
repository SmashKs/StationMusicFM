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

package com.no1.taiwan.stationmusicfm.domain.parameters.history

import com.no1.taiwan.stationmusicfm.UnsupportedOperation
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.domain.models.others.SearchHistModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.ext.consts.Pager

data class SearchHistParams(
    val keyword: String = DEFAULT_STR,
    val limit: Int = Pager.LIMIT,
    val model: SearchHistModel? = null
) : Parameterable {
    companion object {
        const val PARAM_NAME_KEYWORD = "keyword"
        const val PARAM_NAME_LIMIT = "limit"
        const val PARAM_NAME_KEYWORD_MODEL = "model"
    }

    override fun toApiParam() = UnsupportedOperation()

    override fun toApiAnyParam() = AnyParameters().apply {
        if (model != null)
            put(PARAM_NAME_KEYWORD_MODEL, model)
        else
            put(PARAM_NAME_KEYWORD, keyword)
        put(PARAM_NAME_LIMIT, limit)
    }
}
