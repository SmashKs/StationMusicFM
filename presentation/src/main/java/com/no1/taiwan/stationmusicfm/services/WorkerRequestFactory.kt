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

package com.no1.taiwan.stationmusicfm.services

import android.content.res.Resources
import android.util.ArrayMap
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.no1.taiwan.stationmusicfm.services.workers.InitialWorker
import com.no1.taiwan.stationmusicfm.services.workers.PreprocessDataWorker
import com.no1.taiwan.stationmusicfm.services.workers.RankChartHasParsedWorker

object WorkerRequestFactory {
    const val WORKER_INIT = "worker for initializing"
    const val WORKER_CHART_CHECKER = "worker for parse chart from resource"
    const val WORKER_INIT_DATA = "worker for initializing the data from json"

    private val mapping by lazy {
        ArrayMap<String, OneTimeWorkRequest>().apply {
            put(WORKER_INIT, OneTimeWorkRequestBuilder<InitialWorker>().addTag(WORKER_INIT).build())
            put(WORKER_CHART_CHECKER,
                OneTimeWorkRequestBuilder<RankChartHasParsedWorker>().addTag(WORKER_CHART_CHECKER).build())
            put(WORKER_INIT_DATA, OneTimeWorkRequestBuilder<PreprocessDataWorker>().addTag(WORKER_INIT_DATA).build())
        }
    }

    fun getWorkerRequest(tag: String) = mapping[tag] ?: throw Resources.NotFoundException()
}
