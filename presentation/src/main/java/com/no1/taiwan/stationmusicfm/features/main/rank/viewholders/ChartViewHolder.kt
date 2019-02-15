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

package com.no1.taiwan.stationmusicfm.features.main.rank.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import org.jetbrains.anko.find

class ChartViewHolder(view: View) : AdaptiveViewHolder<MultiTypeFactory, RankingIdForChartItem>(view) {
    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: RankingIdForChartItem, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            // Adjust the top two items view only.
            find<CardView>(R.id.mcv_chart).apply {
                if (position == 0 || position == 1) {
                    layoutParams = cast<ViewGroup.MarginLayoutParams>(layoutParams).apply {
                        topMargin = context.resources.getDimension(R.dimen.md_two_unit).toInt()
                    }
                }
            }
            find<TextView>(R.id.ftv_chart).text = model.title
            find<ImageView>(R.id.aiv_thumbnail)
        }
    }
}
