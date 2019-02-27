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
import com.no1.taiwan.stationmusicfm.data.data.others.SearchHistoryData
import com.no1.taiwan.stationmusicfm.data.local.config.BaseDao

@Dao
abstract class SearchingHistoryDao : BaseDao<SearchHistoryData> {
    /**
     * Get all data from the History table.
     */
    @Query("SELECT * FROM table_history LIMIT :limit")
    abstract fun getHistories(limit: Int = 30): List<SearchHistoryData>

    /**
     * Delete a specific data by [keyword] from the History table.
     *
     * @param keyword a keyword about artist, track, album, ...etc.
     */
    @Query("DELETE FROM table_history WHERE keyword=:keyword")
    abstract fun releaseBy(keyword: String)
}
