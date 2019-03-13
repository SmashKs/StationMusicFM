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

package com.no1.taiwan.stationmusicfm.data.data.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.no1.taiwan.stationmusicfm.data.data.Data
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import java.util.Date

@Entity(tableName = "table_local_music", indices = [Index("track_name", "artist_name", unique = true)])
data class LocalMusicData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "track_name")
    val trackName: String = DEFAULT_STR,
    @ColumnInfo(name = "artist_name")
    val artistName: String = DEFAULT_STR,
    val duration: Int = 0,
    @ColumnInfo(name = "has_own")
    val hasOwn: Boolean = false,
    @ColumnInfo(name = "remote_track_uri")
    val remoteTrackUri: String = DEFAULT_STR,
    @ColumnInfo(name = "local_track_uri")
    val localTrackUri: String = DEFAULT_STR,
    @ColumnInfo(name = "cover_uri")
    val coverUri: String = DEFAULT_STR,
    @ColumnInfo(name = "playlist_list")
    val playlistList: String = DEFAULT_STR,
    val created: Date = Date(),
    @ColumnInfo(name = "last_listen")
    val lastListen: Date = Date()
) : Data
