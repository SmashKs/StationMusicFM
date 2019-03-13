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
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.kotlinknifer.toDrawable
import com.devrapid.kotlinshaver.toTimeString
import com.hwangjr.rxbus.RxBus
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.MultiViewHolder
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_PLAY_A_SONG
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.kodein.di.generic.instance

class RankTrackViewHolder(view: View) : MultiViewHolder<SongEntity>(view) {
    private val player: MusicPlayer by instance()

    /**
     * Set the views' properties.
     *
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    override fun initView(model: SongEntity, position: Int, adapter: AdaptiveAdapter<*, *, *>) {
        itemView.apply {
            find<ImageView>(R.id.iv_album).loadByAny(model.oriCoverUrl)
            find<TextView>(R.id.ftv_order).text = (position + 1).toString()
            find<TextView>(R.id.ftv_track_name).text = model.title
            find<TextView>(R.id.ftv_artist_name).text = model.artist
            find<TextView>(R.id.ftv_duration).text = model.length.toTimeString()
            setCurStateIcon(model.url)
            setOnClickListener {
                /**
                 * @event_to [com.no1.taiwan.stationmusicfm.features.main.search.SearchResultFragment.playASong]
                 * @event_to [com.no1.taiwan.stationmusicfm.features.main.rank.RankDetailFragment.playASong]
                 */
                RxBus.get().post(TAG_PLAY_A_SONG, model.url)
                find<ImageView>(R.id.iv_play).image = R.drawable.ic_pause_circle_outline_black.toDrawable(context)
            }
        }
    }

    /**
     * According to the current playing track uri to show the icon.
     *
     * @param uri track's uri.
     */
    private fun setCurStateIcon(uri: String) {
        itemView.apply {
            find<ImageView>(R.id.iv_play).image = (if (player.isPlaying && player.curPlayingUri == uri)
                R.drawable.ic_pause_circle_outline_black
            else
                R.drawable.ic_play_circle_outline_black).toDrawable(context)
        }
    }
}
