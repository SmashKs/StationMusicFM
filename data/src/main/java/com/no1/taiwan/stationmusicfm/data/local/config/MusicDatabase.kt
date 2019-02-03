package com.no1.taiwan.stationmusicfm.data.local.config

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The access operations to a database.
 */
//@Database(entities = [], version = 1)
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
}
