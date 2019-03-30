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

package com.no1.taiwan.stationmusicfm.data.local.services

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.no1.taiwan.stationmusicfm.data.data.playlist.PlaylistInfoData
import com.no1.taiwan.stationmusicfm.data.local.config.BaseDao
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import java.util.Date

@Dao
abstract class PlaylistDao : BaseDao<PlaylistInfoData> {
    /**
     * Get all data from the playlist table.
     */
    @Query("SELECT * FROM table_playlist_info")
    abstract fun retrievePlaylists(): List<PlaylistInfoData>

    /**
     * Get a data from the playlist table.
     *
     * @param id playlist's id.
     */
    @Query("SELECT * FROM table_playlist_info WHERE id=:id")
    abstract fun retrievePlaylist(id: Int): PlaylistInfoData

    @Query("UPDATE table_playlist_info SET track_count=track_count+1 WHERE id=:id")
    abstract fun increaseCountBy(id: Int)

    @Query("UPDATE table_playlist_info SET track_count=track_count-1 WHERE id=:id")
    abstract fun decreaseCountBy(id: Int)

    /**
     * Update a playlist by [id].
     *
     * @param id
     * @param name
     * @param trackNumber
     */
    @Transaction
    open fun replaceBy(id: Int, name: String = DEFAULT_STR, trackNumber: Int = DEFAULT_INT) {
        if (name == DEFAULT_STR && trackNumber == DEFAULT_INT) return
        val playlist = retrievePlaylist(id)
        val newPlaylist = playlist.copy(name = name.takeIf { it != DEFAULT_STR } ?: playlist.name,
                                        trackCount = trackNumber.takeIf { it >= 0 } ?: playlist.trackCount,
                                        updated = Date())
        replace(newPlaylist)
    }

    /**
     * Delete a playlist by [id] from the database.
     *
     * @param id
     */
    @Query("DELETE FROM table_playlist_info WHERE id=:id")
    abstract fun releaseBy(id: Int)
}
