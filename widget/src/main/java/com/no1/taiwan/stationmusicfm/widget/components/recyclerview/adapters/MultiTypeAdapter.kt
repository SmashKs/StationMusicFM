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

package com.no1.taiwan.stationmusicfm.widget.components.recyclerview.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiVisitable
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicViewHolder
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.helpers.AdapterItemTouchHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.SoftReference

/**
 * The common recyclerview adapter for the multi-type object. Avoid that we create
 * a lots similar boilerplate adapters.
 */
open class MultiTypeAdapter(
    override var typeFactory: MultiTypeFactory,
    private val externalDiffUtil: MusicMultiDiffUtil? = null
) : MusicAdapter(), AdapterItemTouchHelper {
    override var dataList = mutableListOf<MusicMultiVisitable>()
        set(value) {
            field = value.toMutableList()
        }
    override var diffUtil: MusicMultiDiffUtil
        get() = externalDiffUtil ?: super.diffUtil
        set(_) = UnsupportedOperation("We don't allow this method to use!")
    protected var viewType = DEFAULT_INT
    private lateinit var recyclerView: SoftReference<RecyclerView>

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // For catching the recycle view.
        this.recyclerView = SoftReference(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        this.viewType = viewType

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onItemSwiped(position: Int) {
        dropAt(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
    }

    /**
     * For loop on the background for not stopping the main thread. In the meantime, update the each viewholders.
     *
     * @param updateBlock (view: RecyclerView.ViewHolder) -> Unit
     */
    protected fun runBackgroundUpdate(updateBlock: (view: RecyclerView.ViewHolder) -> Unit) {
        GlobalScope.launch {
            recyclerView.get()?.apply {
                repeat(childCount) {
                    withContext(Dispatchers.Main) {
                        updateBlock(getChildViewHolder(getChildAt(it)))
                    }
                }
            }
        }
    }
}
