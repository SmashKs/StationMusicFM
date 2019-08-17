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

package com.no1.taiwan.stationmusicfm.services.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankIdsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankIdsReq
import com.no1.taiwan.stationmusicfm.internal.di.PresentationModule
import com.no1.taiwan.stationmusicfm.internal.di.RepositoryModule
import com.no1.taiwan.stationmusicfm.internal.di.UtilModule
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.UsecaseModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.DataMapperModule
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class RankChartHasParsedWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KodeinAware {
    /** A Kodein Aware class must be within reach of a [Kodein] object. */
    override val kodein = Kodein.lazy {
        /** bindings */
        import(UtilModule.utilProvider(context))
        import(PresentationModule.kitsProvider())
        /** usecases are bind here, but the scope is depending on each layer.  */
        import(UsecaseModule.usecaseProvider())
        import(RepositoryModule.repositoryProvider(context))
        import(DataMapperModule.dataUtilProvider())
    }
    private val fetchRankIdsCase: FetchRankIdsCase by instance()

    override fun doWork(): Result {
        // If there're data inside in database already, we can skip storing.
        if (runBlocking { fetchRankIdsCase.exec(FetchRankIdsReq()) }.isNotEmpty())
            return Result.success()

        return Result.failure()
    }
}
