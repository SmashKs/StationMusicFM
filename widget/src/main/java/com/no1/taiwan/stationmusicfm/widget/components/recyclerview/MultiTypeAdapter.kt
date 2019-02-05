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

package com.no1.taiwan.stationmusicfm.widget.components.recyclerview

import android.view.ViewGroup
import com.devrapid.adaptiverecyclerview.AdaptiveDiffUtil
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.helpers.AdapterItemTouchHelper

/**
 * The common recyclerview adapter for the multi-type object. Avoid that we create
 * a lots similar boilerplate adapters.
 */
open class MultiTypeAdapter(
    override var dataList: MultiData,
    override var typeFactory: MultiTypeFactory,
    private val externalDiffUtil: AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>? = null
) : MusicAdapter(), AdapterItemTouchHelper {
    override var diffUtil: AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>
        get() = externalDiffUtil ?: super.diffUtil
        set(_) = throw UnsupportedOperationException("We don't allow this method to use!")
    protected var viewType = DEFAULT_INT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        this.viewType = viewType

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onItemSwiped(position: Int) {
        dropAt(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
    }
}