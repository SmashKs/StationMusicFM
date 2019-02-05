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

package com.no1.taiwan.stationmusicfm.internal.di.dependencies

import com.no1.taiwan.stationmusicfm.domain.usecases.FetchHotListCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchPlaylistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchHotListRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchPlaylistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankMusicRespCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * To provide the necessary usecase objects for the specific activities or fragments.
 */
object UsecaseModule {
    fun usecaseProvider() = Kodein.Module("Use Cases") {
        //region For Fragments
        bind<FetchRankMusicCase>() with singleton { FetchRankMusicRespCase(instance()) }
        bind<FetchPlaylistCase>() with singleton { FetchPlaylistRespCase(instance()) }
        bind<FetchMusicCase>() with singleton { FetchMusicRespCase(instance()) }
        bind<FetchHotListCase>() with singleton { FetchHotListRespCase(instance()) }
        //endregion
    }
}
