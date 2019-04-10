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

package com.no1.taiwan.stationmusicfm.kits.recyclerview.scrolllisteners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinshaver.cast

class LoadMoreScrollListener : RecyclerView.OnScrollListener() {
    companion object Constant {
        private const val VISIBLE_THRESHOLD = 4
    }

    /** The total number of items in the data set after the last load. */
    private var previousTotal = 0
    /** True if we are still waiting for the last set of data to load. */
    private var isLoading = true
    var fetchMoreBlock: (() -> Unit)? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = requireNotNull(recyclerView.layoutManager).itemCount
        val firstVisibleItem =
            cast<LinearLayoutManager>(recyclerView.layoutManager).findFirstVisibleItemPosition()
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            logw(fetchMoreBlock)
            // End has been reached
            fetchMoreBlock?.invoke()
            isLoading = true
        }
    }
}
