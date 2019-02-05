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

package com.no1.taiwan.stationmusicfm.internal.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.work.WorkManager
import com.devrapid.kotlinshaver.cast
import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.no1.taiwan.stationmusicfm.data.data.mappers.HotListMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.MusicMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.MvMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.PlaylistMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.RankChartMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.SongMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.UserMapper
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

/**
 * To provide the necessary utility objects for the whole app.
 */
object UtilModule {
    fun utilProvider(context: Context) = Module("Util Module") {
        bind<WorkManager>() with instance(WorkManager.getInstance())
        // OPTIMIZE(jieyi): 2018/10/16 We might use Gson for mapping data.
        bind<Gson>() with singleton {
            with(GsonBuilder()) {
                setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                setLenient()
                create()
            }
        }

        // Linear Layout Manager.
        bind<LinearLayoutManager>(ObjectLabel.LINEAR_LAYOUT_VERTICAL) with provider {
            LinearLayoutManager(context, VERTICAL, false)
        }
        bind<LinearLayoutManager>(ObjectLabel.LINEAR_LAYOUT_HORIZONTAL) with provider {
            LinearLayoutManager(context, HORIZONTAL, false)
        }
    }

    /**
     * Import this module in the [com.no1.taiwan.newsbasket.App] because data layer needs this.
     */
    fun dataUtilProvider() = Module("Data Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper] */
        bind() from setBinding<DataMapperEntry>()

        /** RankInfoData Layer Mapper */
        bind<DataMapperEntry>().inSet() with singleton { UserMapper::class.java to UserMapper() }
        bind<DataMapperEntry>().inSet() with singleton { RankChartMapper::class.java to RankChartMapper() }
        bind<DataMapperEntry>().inSet() with singleton { MvMapper::class.java to MvMapper() }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            SongMapper::class.java to SongMapper(cast(mapper.toMap()[MvMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            MusicMapper::class.java to MusicMapper(cast(mapper.toMap()[SongMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            val map = mapper.toMap()
            PlaylistMapper::class.java to PlaylistMapper(cast(map[SongMapper::class.java]),
                                                         cast(map[UserMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            HotListMapper::class.java to HotListMapper(cast(mapper.toMap()[PlaylistMapper::class.java]))
        }
    }

    /**
     * Import this module for each activity entry, they don't be needed in the beginning.
     */
    fun presentationUtilProvider(context: Context) = Module("Presentation Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper] */
        bind() from setBinding<PresentationMapperEntry>()

        /** Presentation Layer Mapper */
    }
}
