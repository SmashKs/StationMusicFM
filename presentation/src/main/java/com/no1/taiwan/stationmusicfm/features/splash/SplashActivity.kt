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

package com.no1.taiwan.stationmusicfm.features.splash

import android.os.Bundle
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.BaseActivity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.services.WorkerRequestFactory
import com.no1.taiwan.stationmusicfm.services.workers.PrefetchChartWorker
import com.no1.taiwan.stationmusicfm.services.workers.PrefetchChartWorker.Companion.ARGUMENT_DATA_ID
import com.no1.taiwan.stationmusicfm.services.workers.PrefetchChartWorker.Companion.ARGUMENT_DATA_TITLE
import com.no1.taiwan.stationmusicfm.services.workers.PrefetchChartWorker.Companion.ARGUMENT_DATA_UPDATE
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.widget.components.typeface.TypeFaceProvider
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.kodein.di.generic.instance
import java.util.concurrent.atomic.AtomicInteger

class SplashActivity : BaseActivity() {
    private val workManager: WorkManager by instance()
    private val parserRequest by lazy { WorkerRequestFactory.getWorkerRequest(WorkerRequestFactory.WORKER_CHART_CHECKER) }
    private var counter = AtomicInteger(0)
    private var workersSize = 0

    override fun provideLayoutId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Preload the typeface.
        listOf("santio_regular.otf")
            .forEach { TypeFaceProvider.getTypeFace(applicationContext, it) }

        observeNonNull(workManager.getWorkInfoByIdLiveData(parserRequest.id)) {
            if (state.isFinished) {
                when (state) {
                    WorkInfo.State.FAILED -> {
                        resources.getStringArray(R.array.chart_rank)
                            .apply { workersSize = size }
                            .asSequence()
                            .map { it.split("|") }  // Split the data to a list by "|".
                            .map {
                                Data.Builder().putInt(ARGUMENT_DATA_ID, it[0].toInt())
                                    .putString(ARGUMENT_DATA_TITLE, it[1]).putString(ARGUMENT_DATA_UPDATE, it[2])
                                    .build()
                            }  // Build data for the workers' parameter.
                            .map { OneTimeWorkRequestBuilder<PrefetchChartWorker>().setInputData(it).build() }
                            .forEach {
                                // Observe all workers process.
                                observeNonNull(workManager.getWorkInfoByIdLiveData(it.id)) {
                                    if (state.isFinished) {
                                        counter.incrementAndGet()
                                        if (counter.get() == workersSize) {
                                            gotoMainMusic()
                                        }
                                    }
                                }
                                // Put them into queue for processing.
                                workManager.enqueue(it)
                            }
                    }
                    WorkInfo.State.SUCCEEDED -> gotoMainMusic()
                    else -> toast("Something wrong with your phone, please relaunch app again.")
                }
            }
        }
    }

    private fun gotoMainMusic() {
        startActivity<MainActivity>()
        finish()
    }
}
