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
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.kotlinknifer.DrawableDirectionConst.DRAWABLE_DIRECTION_START
import com.devrapid.kotlinknifer.addDrawable
import com.devrapid.kotlinknifer.getColor
import com.hwangjr.rxbus.RxBus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreIndexFragment.Companion.FRAGMENT_TARGET_ARTIST
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_DETAIL_TARGET
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import org.jetbrains.anko.find

class ExploreArtistViewHolder(view: View) : AdaptiveViewHolder<MultiTypeFactory, ArtistInfoEntity.ArtistEntity>(view) {
    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: ArtistInfoEntity.ArtistEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            find<ImageView>(R.id.iv_artist).loadByAny(model.images.last().text)
            find<TextView>(R.id.ftv_artist_name).text = model.name
            find<TextView>(R.id.ftv_favorite).apply {
                addDrawable(R.drawable.ic_heart_black,
                            getColor(R.color.colorPrimaryTextV1),
                            DRAWABLE_DIRECTION_START,
                            .5f,
                            .5f)
                text = model.listeners
            }
            find<CardView>(R.id.mcv_artist).setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.explore.ExploreIndexFragment.gotoNextDetailFragment] */
                RxBus.get().post(TAG_TO_DETAIL, hashMapOf(PARAMS_TO_DETAIL_TARGET to FRAGMENT_TARGET_ARTIST,
                                                          PARAMS_COMMON_MBID to model.mbid,
                                                          PARAMS_COMMON_ARTIST_NAME to model.name))
            }
        }
    }
}
