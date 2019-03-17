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
import com.no1.taiwan.stationmusicfm.data.data.playlist.LocalMusicData
import com.no1.taiwan.stationmusicfm.data.local.config.BaseDao
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import java.util.Date

@Dao
abstract class LocalMusicDao : BaseDao<LocalMusicData> {
    /**
     * Insert a data if there's the same data inside the database, it will be replaced.
     *
     * @param newData
     */
    @Transaction
    open fun insertBy(newData: LocalMusicData) {
        val existMusic = retrieveMusic(newData.trackName, newData.artistName)
        var updatedData = newData
        // Update the download date if the data isn't download information.
        if (!newData.hasOwn || (newData.hasOwn && existMusic?.hasOwn == true))
            updatedData = newData.copy(downloaded = Date(0),
                                       lastListen = if (!updatedData.hasOwn)
                                           updatedData.lastListen
                                       else
                                           existMusic?.lastListen ?: updatedData.lastListen)
        if (existMusic == null)
            insert(updatedData)
        else
            replace(existMusic.copy(hasOwn = updatedData.hasOwn.takeIf { it } ?: existMusic.hasOwn,
                                    remoteTrackUri = updatedData.remoteTrackUri.takeIf { it != DEFAULT_STR } ?: existMusic.remoteTrackUri,
                                    localTrackUri = updatedData.localTrackUri.takeIf { it != DEFAULT_STR } ?: existMusic.localTrackUri,
                                    coverUri = updatedData.coverUri.takeIf { it != DEFAULT_STR } ?: existMusic.coverUri,
                                    playlistList = updatedData.playlistList.takeIf { it != DEFAULT_STR } ?: existMusic.playlistList,
                                    lastListen = updatedData.lastListen))
    }

    /**
     * Get all data from the local music table.
     */
    @Query("SELECT * FROM table_local_music")
    abstract fun retrieveMusics(): List<LocalMusicData>

    /**
     * Get one data from the local music table.
     *
     * @param track
     * @param artist
     */
    @Query("SELECT * FROM table_local_music WHERE track_name=:track AND artist_name=:artist")
    abstract fun retrieveMusic(track: String, artist: String): LocalMusicData?

    /**
     * Remove a music from local music table.
     *
     * @param id local music's id.
     */
    @Query("DELETE FROM table_local_music WHERE id=:id")
    abstract fun releaseBy(id: Int)
}
