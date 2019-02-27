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

package com.no1.taiwan.stationmusicfm.data.local.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.no1.taiwan.stationmusicfm.data.data.others.RankingIdData
import com.no1.taiwan.stationmusicfm.data.data.others.SearchHistoryData
import com.no1.taiwan.stationmusicfm.data.local.converters.DateConvert
import com.no1.taiwan.stationmusicfm.data.local.services.RankingDao
import com.no1.taiwan.stationmusicfm.data.local.services.SearchingHistoryDao

/**
 * The access operations to a database.
 */
@Database(entities = [RankingIdData::class, SearchHistoryData::class], version = 1, exportSchema = false)
@TypeConverters(DateConvert::class)
abstract class MusicDatabase : RoomDatabase() {
    companion object {
        @Volatile private var INSTANCE: MusicDatabase? = null
        private const val DATABASE_NAME = "music.db"

        fun getDatabase(context: Context): MusicDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

    abstract fun createRankingDao(): RankingDao
    abstract fun createSearchHistoryDao(): SearchingHistoryDao
}
