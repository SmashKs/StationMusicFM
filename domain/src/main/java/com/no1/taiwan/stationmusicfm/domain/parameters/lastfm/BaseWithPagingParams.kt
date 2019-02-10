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

import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.domain.parameters.BaseParams
import com.no1.taiwan.stationmusicfm.ext.consts.Pager

open class BaseWithPagingParams : BaseParams() {
    companion object {
        const val PARAM_NAME_LIMIT = "limit"
        const val PARAM_NAME_PAGE = "page"

        private const val PARAM_QUERY_LIMIT = Pager.LIMIT
        private const val PARAM_QUERY_PAGE = Pager.PAGE
    }

    open var limit = PARAM_QUERY_LIMIT
    open var page = PARAM_QUERY_PAGE

    override fun toApiParam() = super.toApiParam().apply {
        page.takeIf { PARAM_QUERY_PAGE != it }?.let { put(PARAM_NAME_PAGE, it.toString()) }
        put(PARAM_NAME_LIMIT, limit.toString())
    }

    override fun toApiAnyParam(): AnyParameters = hashMapOf(PARAM_NAME_FORMAT to format)
}
