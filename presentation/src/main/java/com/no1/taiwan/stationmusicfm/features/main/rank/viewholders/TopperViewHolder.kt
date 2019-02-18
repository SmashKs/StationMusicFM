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
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.kotlinknifer.getDisplayMetrics
import com.devrapid.kotlinknifer.resizeView
import com.hwangjr.rxbus.RxBus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_RANK_EVENT
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import org.jetbrains.anko.find

class TopperViewHolder(view: View) : AdaptiveViewHolder<MultiTypeFactory, RankingIdEntity>(view) {
    companion object {
        private const val CONST_SIZE_RATIO_WIDTH = .5
        private const val CONST_SIZE_RATIO_HEIGHT = .75
    }

    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: RankingIdEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            val (width, height) = context.getDisplayMetrics().let {
                val halfWidth = (it.widthPixels * CONST_SIZE_RATIO_WIDTH).toInt()
                halfWidth to (halfWidth * CONST_SIZE_RATIO_HEIGHT).toInt()
            }
            resizeView(width, height)
            find<TextView>(R.id.ftv_chart).text =
                listOf(model.title, model.update, "${model.trackNumber} tracks").joinToString("\n")
            find<ImageView>(R.id.aiv_thumbnail).loadByAny(model.topTrackUri, context)
            /** @event_to [com.no1.taiwan.stationmusicfm.features.main.rank.RankIndexFragment.gotoDetailFragment] */
            find<CardView>(R.id.mcv_chart).setOnClickListener { RxBus.get().post(TAG_RANK_EVENT, model.id) }
        }
    }
}
