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

package com.no1.taiwan.stationmusicfm.kits.recyclerview.adapter

import com.devrapid.kotlinshaver.castOrNull
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackWithStreamableEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotTrackViewHolder
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicViewHolder
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.adapters.MultiTypeAdapter

class NotifiableAdapter(
    override var typeFactory: MultiTypeFactory,
    externalDiffUtil: MusicMultiDiffUtil? = null
) : MultiTypeAdapter(typeFactory, externalDiffUtil) {
    var playingPosition = -1
        set(value) {
            field = value
            // Update all child views which are showing on the screen.
            runBackgroundUpdate { castOrNull<Notifiable>(it)?.notifyChange(value) }
        }

    /**
     * Called when a view created by this adapter has been attached to a window.
     *
     * This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * [onViewDetachedFromWindow][.onViewDetachedFromWindow]
     * those resources should be restored here.
     *
     * @param holder Holder of the view being attached
     */
    override fun onViewAttachedToWindow(holder: MusicViewHolder) {
        super.onViewAttachedToWindow(holder)
        // In the beginning we don't need to set again, this is for clicking an item then notify others.
        if (playingPosition != DEFAULT_INT)
        // For updating views are out of the screen but didn't go to attached scrap buffer.
            castOrNull<Notifiable>(holder)?.notifyChange(playingPosition)
        // OPTIMIZE(jieyi): 2019-03-15 Workaround for setting icon again because the viewpager shows again will
        //  call [notifyChange] to override the correct icon.
        castOrNull<HotTrackViewHolder>(holder)?.setOptionIcon()
    }

    fun reassignTrackUri(uri: String) {
        if (playingPosition < 0) return
        when (val data = dataList[playingPosition]) {
            is TrackWithStreamableEntity -> data.url = uri
        }
    }

    fun retrieveTrackUri(index: Int): String? {
        if (playingPosition < 0) return null
        return when (val data = dataList[index]) {
            is CommonMusicEntity.SongEntity -> data.url
            else -> null
        }
    }

    fun setStateMusicBy(position: Int, isSuccessToPlay: Boolean) {
        // Update all child views which are showing on the screen.
        runBackgroundUpdate { castOrNull<Notifiable>(it)?.notifyChange(position, isSuccessToPlay) }
    }
}
