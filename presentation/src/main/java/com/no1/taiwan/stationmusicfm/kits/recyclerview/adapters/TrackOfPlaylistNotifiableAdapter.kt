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

package com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters

import com.devrapid.kotlinshaver.bkg
import com.devrapid.kotlinshaver.castOrNull
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.kits.recyclerview.viewholder.Notifiable
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.adapters.MultiTypeAdapter
import kotlinx.coroutines.delay

class TrackOfPlaylistNotifiableAdapter(
    override var typeFactory: MultiTypeFactory,
    externalDiffUtil: MusicMultiDiffUtil? = null
) : MultiTypeAdapter(typeFactory, externalDiffUtil) {
    fun updateViewHolderItems() {
        bkg {
            // NOTE: Avoid updating is too fast to be correct. That's why it should delay for a while.
            delay(150)
            // It will change whole items, position can be ignored.
            executeUpdate {
                castOrNull<Notifiable>(it)?.notifyChange(DEFAULT_INT)
            }
        }
    }
}
