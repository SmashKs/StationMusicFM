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

package com.no1.taiwan.stationmusicfm.internal.di.tags

/**
 * The tags for identifying the same object data type in Dependency Injection.
 */
object ObjectLabel {
    const val LINEAR_LAYOUT_VERTICAL = "vertical"
    const val LINEAR_LAYOUT_HORIZONTAL = "horizontal"

    const val FRAGMENT_BUS_SHORT_LIFE = "fragment short life from onResume to onPause"
    const val FRAGMENT_BUS_LONG_LIFE = "fragment long life from onStart to onDestroy"

    const val UTIL_DIFF_KEYWORD = "recycler diff util keyword"
    const val UTIL_DIFF_RANK = "recycler diff rank"

    const val ITEM_DECORATION_ACTION_BAR_BLANK = "recycler item decoration action bar blank"
    const val ITEM_DECORATION_SEPARATOR = "recycler item decoration separator"

    const val ADAPTER_HISTORY = "for history adapter"
    const val ADAPTER_RANK = "for rank adapter"
}
