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

package com.no1.taiwan.stationmusicfm.kits.recyclerview

import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiVisitable

class RankDiffUtil : MusicMultiDiffUtil() {
    override var newList = mutableListOf<MusicMultiVisitable>()
    override var oldList = mutableListOf<MusicMultiVisitable>()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        cast<RankingIdForChartItem>(newList[newItemPosition]).topTrackUri == cast<RankingIdForChartItem>(oldList[oldItemPosition]).topTrackUri

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        cast<RankingIdForChartItem>(newList[newItemPosition]).topTrackUri == cast<RankingIdForChartItem>(oldList[oldItemPosition]).topTrackUri

    override fun getNewListSize() = newList.size

    override fun getOldListSize() = oldList.size
}
