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
import com.no1.taiwan.stationmusicfm.data.data.others.SearchHistoryData
import com.no1.taiwan.stationmusicfm.data.local.config.BaseDao
import java.util.Date

@Dao
abstract class SearchingHistoryDao : BaseDao<SearchHistoryData> {
    /**
     * Insert a history data into database and check the keyword if they are the same,
     * just updated the date; otherwise, insert a new data.
     *
     * @param keyword a keyword about artist, track, album, ...etc.
     */
    @Transaction
    open fun insertBy(keyword: String) {
        val history = retrieveHistory(keyword)
        if (history == null)
            insert(SearchHistoryData(0, keyword, Date()))
        else
            replace(history.copy(updated = Date()))
    }

    /**
     * Get all data from the History table.
     */
    @Query("SELECT * FROM table_history ORDER BY updated DESC LIMIT :limit")
    abstract fun retrieveHistories(limit: Int = 30): List<SearchHistoryData>

    /**
     * Get a data with specific [keyword] from the History table
     *
     * @param keyword a keyword about artist, track, album, ...etc.
     * @return SearchHistoryData
     */
    @Query("SELECT * FROM table_history WHERE keyword=:keyword")
    abstract fun retrieveHistory(keyword: String): SearchHistoryData?

    /**
     * Delete a specific data by [keyword] from the History table.
     *
     * @param keyword a keyword about artist, track, album, ...etc.
     */
    @Query("DELETE FROM table_history WHERE keyword=:keyword")
    abstract fun releaseBy(keyword: String)
}
