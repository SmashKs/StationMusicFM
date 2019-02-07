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
import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.HotListDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.MusicDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.MvDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.RankChartDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.SongDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.SongListDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.UserDMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.HotListPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MvPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.RankChartPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.SongListPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.SongPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.UserPMapper
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
            SongDMapper::class.java to SongDMapper(MvDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton {
            MusicDMapper::class.java to MusicDMapper(SongDMapper(MvDMapper()))
        }
        bind<DataMapperEntry>().inSet() with singleton {
            SongListDMapper::class.java to SongListDMapper(SongDMapper(MvDMapper()), UserDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton {
            HotListDMapper::class.java to HotListDMapper(SongListDMapper(SongDMapper(MvDMapper()), UserDMapper()))
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
            SongPMapper::class.java to SongPMapper(MvPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            MusicPMapper::class.java to MusicPMapper(SongPMapper(MvPMapper()))
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            SongListPMapper::class.java to SongListPMapper(SongPMapper(MvPMapper()), UserPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            HotListPMapper::class.java to HotListPMapper(SongListPMapper(SongPMapper(MvPMapper()), UserPMapper()))
        }
    }
}
