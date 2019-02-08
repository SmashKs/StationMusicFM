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

package com.no1.taiwan.stationmusicfm.domain.usecases.lastfm

import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.parameters.lastfm.BaseWithPagingParams
import com.no1.taiwan.stationmusicfm.domain.repositories.LastFmRepository
import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase.RequestValues
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTagReq

class FetchChartTopTagRespCase(
    private val repository: LastFmRepository
) : FetchChartTopTagCase() {
    /** Provide a common parameter variable for the children class. */
    override var requestValues: FetchChartTopTagReq? = null

    override suspend fun acquireCase() = attachParameter {
        repository.fetchChartTopTag(it.parameters)
    }

    class Request(val parameters: Parameterable = BaseWithPagingParams()) : RequestValues
}
