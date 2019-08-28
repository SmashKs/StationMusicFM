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
import com.devrapid.kotlinshaver.toTimeString
import com.hwangjr.rxbus.Bus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.ktx.image.load
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_REMOVING_LOCAL_MUSIC_FROM_PLAYLIST
import org.jetbrains.anko.find
import org.kodein.di.generic.instance

class LocalMusicViewHolder(view: View) : MultiViewHolder<LocalMusicEntity>(view), Notifiable {
    private val emitter: Bus by instance()

    /**
     * Set the views' properties.
     *
     * @param model     a data model after input from a list.
     * @param position  the index of a list.
     * @param adapter   parent adapter.
     */
    override fun initView(
        model: LocalMusicEntity,
        position: Int,
        adapter: AdaptiveAdapter<*, *, *>
    ) {
        itemView.apply {
            find<TextView>(R.id.ftv_playlist_index).text = (layoutPosition + 1).toString()
            find<TextView>(R.id.ftv_music_name).text = model.trackName
            find<ImageView>(R.id.iv_thumbnail).load(model.coverUri)
            find<TextView>(R.id.ftv_artist_name).text = model.artistName
            find<TextView>(R.id.ftv_track_duration).text = model.duration.toTimeString()
            setOnClickListener {
            }
            /** @event_to [com.no1.taiwan.stationmusicfm.features.main.mymusic.MyMusicDetailFragment.openBottomSheetOptions] */
            setOnLongClickListener {
                emitter.post(TAG_REMOVING_LOCAL_MUSIC_FROM_PLAYLIST, model)
                true
            }
        }
    }

    override fun notifyChange(position: Int) {
        itemView.find<TextView>(R.id.ftv_playlist_index).text = (layoutPosition + 1).toString()
    }

    override fun notifyChange(position: Int, isSuccessToPlay: Boolean) {
        // Do noop.
    }
}
