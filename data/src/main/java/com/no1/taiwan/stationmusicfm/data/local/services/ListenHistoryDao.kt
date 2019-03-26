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
import com.no1.taiwan.stationmusicfm.data.data.playlist.LocalMusicData
import java.util.Date

@Dao
abstract class ListenHistoryDao {
    /**
     * Get all data from the music table with limitation.
     *
     * @param limit the limitation of the histories.
     * @param zeroTime
     */
    @Query("SELECT * FROM table_local_music WHERE last_listen_date>=:zeroTime ORDER BY last_listen_date ASC LIMIT :limit")
    abstract fun retrieveHistories(limit: Int = 30, zeroTime: Date = Date(0)): List<LocalMusicData>

    /**
     * Get data which has existed in the storage from the the music table.
     *
     * @param limit
     * @param zeroTime
     * @return
     */
    @Query("SELECT * FROM table_local_music WHERE local_track_uri <> '' AND last_listen_date>=:zeroTime ORDER BY last_listen_date ASC LIMIT :limit")
    abstract fun retrieveDownloadedMusics(limit: Int = 30, zeroTime: Date = Date(0)): List<LocalMusicData>

    /**
     * Get data which in the playlist from the the music table.
     *
     * @param playlistId
     * @param limit
     * @param zeroTime
     * @return
     */
    @Query("SELECT * FROM table_local_music WHERE playlist_list LIKE :playlistId AND last_listen_date>=:zeroTime ORDER BY last_listen_date ASC LIMIT :limit")
    abstract fun retrieveTypeOfMusics(playlistId: Int, limit: Int = 30, zeroTime: Date = Date(0)): List<LocalMusicData>

    /**
     * Reset the update time of a history.
     *
     * @param id history's id.
     * @param clearDate init time for not getting it.
     */
    @Query("UPDATE table_local_music SET last_listen_date=:clearDate WHERE id=:id")
    abstract fun releaseHistory(id: Int, clearDate: Date = Date(0))
}
