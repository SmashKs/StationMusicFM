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
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.hwangjr.rxbus.Bus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.AlbumWithArtistTypeGenreEntity
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.ktx.image.load
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_URI
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_ALBUM
import org.jetbrains.anko.find
import org.kodein.di.generic.instance

class AlbumOfGenreViewHolder(view: View) : MultiViewHolder<AlbumWithArtistTypeGenreEntity>(view) {
    private val emitter: Bus by instance()

    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(
        model: AlbumWithArtistTypeGenreEntity,
        position: Int,
        adapter: AdaptiveAdapter<*, *, *>
    ) {
        itemView.apply {
            find<ImageView>(R.id.iv_album_thumbnail).load(model.images.last().text)
            find<TextView>(R.id.ftv_album_name).text = model.name
            find<View>(R.id.cl_album).setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.explore.ExploreGenreFragment.navToAlbumDetail] */
                emitter.post(TAG_TO_ALBUM, hashMapOf(PARAMS_COMMON_MBID to model.mbid,
                                                     PARAMS_TO_ALBUM_NAME to model.name,
                                                     PARAMS_TO_ALBUM_URI to model.images.last().text,
                                                     PARAMS_COMMON_ARTIST_NAME to model.artist.name,
                                                     "uri" to model.artist.images.takeIf { it.isNotEmpty() }?.last()?.text.orEmpty()))
            }
        }
    }
}
