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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.toDrawable
import com.devrapid.kotlinshaver.toTimeString
import com.hwangjr.rxbus.Bus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackWithStreamableEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_LAYOUT_POSITION
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_KEYWORD
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_SEARCH_MUSIC_BY_KEYWORD
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.kodein.di.generic.instance

class HotTrackViewHolder(view: View) : MultiViewHolder<TrackWithStreamableEntity>(view), Notifiable {
    private var trackUri = DEFAULT_STR
    private val player: MusicPlayer by instance()
    private val emitter: Bus by instance()

    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: TrackWithStreamableEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            find<TextView>(R.id.ftv_track_name).text = "${position + 1}\t${model.name}"
            model.duration.takeIf { it.isNotBlank() }?.let {
                find<TextView>(R.id.ftv_duration).text = it.toInt().toTimeString()
            }
            trackUri = model.url
            setCurStateIcon(!player.isPlaying || player.curPlayingUri != model.url)
            find<ImageButton>(R.id.ib_option).setOnClickListener {
                /** @event_to [com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerTrackFragment.searchMusic] */
                emitter.post(PARAMS_SEARCH_MUSIC_BY_KEYWORD, hashMapOf(PARAMS_LAYOUT_POSITION to layoutPosition,
                                                                       PARAMS_SEARCH_KEYWORD to "${model.artist.name} ${model.name}"))
            }
        }
    }

    override fun notifyChange(position: Int) {
        setCurStateIcon(position != layoutPosition)
    }

    /**
     * According to the current playing track uri to show the icon.
     *
     * @param isIdle
     */
    private fun setCurStateIcon(isIdle: Boolean) {
        itemView.apply {
            logw("???????????????????????????????????????????????????????????", isIdle, layoutPosition, trackUri)
            find<ImageView>(R.id.ib_option).image = (if (isIdle)
                R.drawable.ic_play_circle_outline_black
            else
                R.drawable.ic_pause_circle_outline_black).toDrawable(context)
        }
    }
}
