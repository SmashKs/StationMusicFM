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

package com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.hwangjr.rxbus.Bus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.ktx.image.load
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_PLAYLIST_DETAIL
import org.kodein.di.generic.instance

class PlaylistViewHolder(view: View) : MultiViewHolder<PlaylistInfoEntity>(view) {
    private val emitter: Bus by instance()

    /**
     * Set the views' properties.
     *
     * @param model     a data model after input from a list.
     * @param position  the index of a list.
     * @param adapter   parent adapter.
     */
    override fun initView(
        model: PlaylistInfoEntity,
        position: Int,
        adapter: AdaptiveAdapter<*, *, *>
    ) {
        itemView.apply {
            find<ImageView>(R.id.iv_playlist_thumbnail).load(R.drawable.thumbnail_default_playlist)
            find<TextView>(R.id.ftv_playlist_name).text = model.name
            find<TextView>(R.id.ftv_track_count).text =
                context.getString(R.string.viewholder_playlist_song).format(model.trackCount)
            /** @event_to [com.no1.taiwan.stationmusicfm.features.main.mymusic.MyMusicIndexFragment.gotoPlaylistDetail] */
            setOnClickListener {
                emitter.post(TAG_TO_PLAYLIST_DETAIL, model.copy())
            }
        }
    }
}
