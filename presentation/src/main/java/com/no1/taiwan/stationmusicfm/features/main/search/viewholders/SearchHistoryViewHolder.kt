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

package com.no1.taiwan.stationmusicfm.features.main.search.viewholders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.hwangjr.rxbus.RxBus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.others.SearchHistoryEntity
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_REMOVING_SEARCH_HIST
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_SAVING_SEARCH_HIST
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import org.jetbrains.anko.find

class SearchHistoryViewHolder(view: View) : AdaptiveViewHolder<MultiTypeFactory, SearchHistoryEntity>(view) {
    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: SearchHistoryEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            find<TextView>(R.id.ftv_history).text = model.keyword
            find<ImageButton>(R.id.ib_remove).setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.search.SearchIndexFragment.removeKeyword] */
                RxBus.get().post(TAG_REMOVING_SEARCH_HIST, model.keyword)
            }
            setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.search.SearchIndexFragment.searchByHistory] */
                RxBus.get().post(TAG_SAVING_SEARCH_HIST, model.keyword)
            }
        }
    }
}
