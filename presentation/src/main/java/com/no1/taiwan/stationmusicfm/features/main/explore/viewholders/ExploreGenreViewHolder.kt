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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewholders

import android.view.View
import android.widget.TextView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.hwangjr.rxbus.Bus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity.TagEntity
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreIndexFragment.Companion.FRAGMENT_TARGET_GENRE
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_DETAIL_TARGET
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_GENRE_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import org.kodein.di.generic.instance

class ExploreGenreViewHolder(view: View) : MultiViewHolder<TagEntity>(view) {
    private val emitter: Bus by instance()

    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: TagEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            find<TextView>(R.id.ftv_genre).text = model.name
            setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.explore.ExploreIndexFragment.gotoNextDetailFragment] */
                emitter.post(TAG_TO_DETAIL,
                             hashMapOf(PARAMS_TO_DETAIL_TARGET to FRAGMENT_TARGET_GENRE,
                                       PARAMS_TO_GENRE_NAME to model.name))
            }
        }
    }
}
