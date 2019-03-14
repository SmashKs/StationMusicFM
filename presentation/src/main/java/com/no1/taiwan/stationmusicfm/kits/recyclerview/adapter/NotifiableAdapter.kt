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

package com.no1.taiwan.stationmusicfm.kits.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.SoftRef
import com.devrapid.kotlinshaver.bkg
import com.devrapid.kotlinshaver.castOrNull
import com.devrapid.kotlinshaver.uiSwitch
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicViewHolder
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.adapters.MultiTypeAdapter

class NotifiableAdapter(
    override var typeFactory: MultiTypeFactory,
    externalDiffUtil: MusicMultiDiffUtil? = null
) : MultiTypeAdapter(typeFactory, externalDiffUtil) {
    private var recyclerView by SoftRef<RecyclerView>()
    var playingPosition = -1
        set(value) {
            field = value
            // Update all child views which are showing on the screen.
            bkg {
                recyclerView?.apply {
                    repeat(childCount) {
                        uiSwitch { castOrNull<Notifiable>(getChildViewHolder(getChildAt(it)))?.notifyChange(value) }
                    }
                }
            }
        }

    /**
     * Called when a view created by this adapter has been attached to a window.
     *
     *
     * This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * [onViewDetachedFromWindow][.onViewDetachedFromWindow]
     * those resources should be restored here.
     *
     * @param holder Holder of the view being attached
     */
    override fun onViewAttachedToWindow(holder: MusicViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as? Notifiable)?.notifyChange(playingPosition)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // For catching the recycleview.
        this.recyclerView = recyclerView
    }
}
