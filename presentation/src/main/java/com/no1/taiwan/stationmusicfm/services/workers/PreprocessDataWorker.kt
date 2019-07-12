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
import com.google.gson.Gson
import com.no1.taiwan.stationmusicfm.domain.parameters.EmptyParams
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistParams
import com.no1.taiwan.stationmusicfm.domain.usecases.AddPlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddPlaylistsReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchPlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchPlaylistsReq
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.BufferedReader

class PreprocessDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KodeinAware {
    /** A Kodein Aware class must be within reach of a [Kodein] object. */
    override val kodein by kodein(context)
    private val gson: Gson by instance()
    private val addPlaylistsCase: AddPlaylistsCase by instance()
    private val fetchPlaylistsCase: FetchPlaylistsCase by instance()

    override fun doWork(): Result {
        val res = runBlocking {
            if (fetchPlaylistsCase.exec(FetchPlaylistsReq(EmptyParams())).isNotEmpty()) return@runBlocking true
            val json = applicationContext.assets.open("json/playlist.json").use {
                it.bufferedReader().use(BufferedReader::readText)
            }
            val list = gson.fromJson(json, Array<PlaylistInfoEntity>::class.java).toList()
            val ids = list.map(PlaylistInfoEntity::id)
            val names = list.map(PlaylistInfoEntity::name)
            addPlaylistsCase.exec(AddPlaylistsReq(PlaylistParams(ids, names)))
        }
        return if (res) Result.success() else Result.failure()
        return Result.success()
    }
}
