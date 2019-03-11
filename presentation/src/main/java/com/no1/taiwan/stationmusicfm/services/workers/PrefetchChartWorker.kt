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
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.RankParams
import com.no1.taiwan.stationmusicfm.domain.usecases.AddRankIdsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddRankIdsReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.others.RankingPMapper
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.internal.di.PresentationModule
import com.no1.taiwan.stationmusicfm.internal.di.RepositoryModule
import com.no1.taiwan.stationmusicfm.internal.di.UtilModule
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.UsecaseModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.DataMapperModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.PresentationMapperModule
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class PrefetchChartWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KodeinAware {
    companion object {
        const val ARGUMENT_DATA_ID = "argument id"
        const val ARGUMENT_DATA_TITLE = "argument title"
        const val ARGUMENT_DATA_UPDATE = "argument updated period"
    }

    /** A Kodein Aware class must be within reach of a [Kodein] object. */
    override val kodein = Kodein.lazy {
        /** bindings */
        import(UtilModule.utilProvider(applicationContext))
        import(PresentationModule.kitsProvider())
        /** usecases are bind here but the scope is depending on each layers.  */
        import(UsecaseModule.usecaseProvider())
        import(RepositoryModule.repositoryProvider(applicationContext))
        import(DataMapperModule.dataUtilProvider())
        import(PresentationMapperModule.presentationUtilProvider())
    }
    private val fetchRankMusicCase: FetchRankMusicCase by instance()
    private val addRankIdsCase: AddRankIdsCase by instance()
    private val mapperPool: PreziMapperPool by instance()
    private val musicMapper by lazy { cast<MusicPMapper>(mapperPool[MusicPMapper::class.java]) }
    private val rankingMapper by lazy { cast<RankingPMapper>(mapperPool[RankingPMapper::class.java]) }
    private val id by lazy { inputData.getInt(ARGUMENT_DATA_ID, DEFAULT_INT) }
    private val title by lazy { requireNotNull(inputData.getString(ARGUMENT_DATA_TITLE)) }
    private val update by lazy { requireNotNull(inputData.getString(ARGUMENT_DATA_UPDATE)) }

    override fun doWork(): Result {
        runBlocking {
            val chart = fetchRankMusicCase.execMapping(musicMapper,
                                                       FetchRankMusicReq(RankParams(id)))
                .let {
                    RankingIdEntity(id, title, update, it.songs.first().oriCoverUrl, it.songs.size)
                }

            addRankIdsCase.exec(AddRankIdsReq(listOf(rankingMapper.toModelFrom(chart))))
        }

        return Result.success()
    }
}
