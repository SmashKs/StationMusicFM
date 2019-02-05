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
import com.no1.taiwan.stationmusicfm.data.data.mappers.HotListDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.MusicDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.MvDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.PlaylistDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.RankChartDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.SongDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.UserDMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.HotListPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.MvPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.PlaylistPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.RankChartPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.SongPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.UserPMapper
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
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.entities.mappers.Mapper] */
        bind() from setBinding<DataMapperEntry>()

        /** RankInfoData Layer Mapper */
        bind<DataMapperEntry>().inSet() with singleton { UserDMapper::class.java to UserDMapper() }
        bind<DataMapperEntry>().inSet() with singleton { RankChartDMapper::class.java to RankChartDMapper() }
        bind<DataMapperEntry>().inSet() with singleton { MvDMapper::class.java to MvDMapper() }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            SongDMapper::class.java to SongDMapper(cast(mapper.toMap()[MvDMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            MusicDMapper::class.java to MusicDMapper(cast(mapper.toMap()[SongDMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            val map = mapper.toMap()
            PlaylistDMapper::class.java to PlaylistDMapper(cast(map[SongDMapper::class.java]),
                                                           cast(map[UserDMapper::class.java]))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            val mapper: Set<DataMapperEntry> by kodein.instance()
            HotListDMapper::class.java to HotListDMapper(cast(mapper.toMap()[PlaylistDMapper::class.java]))
        }
    }

    /**
     * Import this module for each activity entry, they don't be needed in the beginning.
     */
    fun presentationUtilProvider() = Module("Presentation Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.entities.mappers.Mapper] */
        bind() from setBinding<PreziMapperEntry>()

        /** Presentation Layer Mapper */
        bind<PreziMapperEntry>().inSet() with singleton { UserPMapper::class.java to UserPMapper() }
        bind<PreziMapperEntry>().inSet() with singleton { RankChartPMapper::class.java to RankChartPMapper() }
        bind<PreziMapperEntry>().inSet() with singleton { MvPMapper::class.java to MvPMapper() }
        bind<PreziMapperEntry>().inSet() with singleton {
            val mapper: Set<PreziMapperEntry> by kodein.instance()
            SongPMapper::class.java to SongPMapper(cast(mapper.toMap()[MvPMapper::class.java]))
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            val mapper: Set<PreziMapperEntry> by kodein.instance()
            MusicPMapper::class.java to MusicPMapper(cast(mapper.toMap()[SongPMapper::class.java]))
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            val mapper: Set<PreziMapperEntry> by kodein.instance()
            val map = mapper.toMap()
            PlaylistPMapper::class.java to PlaylistPMapper(cast(map[SongPMapper::class.java]),
                                                           cast(map[UserPMapper::class.java]))
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            val mapper: Set<PreziMapperEntry> by kodein.instance()
            HotListPMapper::class.java to HotListPMapper(cast(mapper.toMap()[PlaylistPMapper::class.java]))
        }
    }
}
