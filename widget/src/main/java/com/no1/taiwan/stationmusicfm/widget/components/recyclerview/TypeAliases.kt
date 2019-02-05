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

package com.no1.taiwan.stationmusicfm.widget.components.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveDiffUtil
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.adaptiverecyclerview.IVisitable

typealias ViewHolderEntry = Pair<Int, Pair<Int, (View) -> RecyclerView.ViewHolder>>
typealias ViewHolderEntries = Set<ViewHolderEntry>

typealias MusicViewHolder = AdaptiveViewHolder<MultiTypeFactory, MusicMultiVisitable>
typealias MusicMultiVisitable = IVisitable<MultiTypeFactory>
typealias MusicAdapter = AdaptiveAdapter<MultiTypeFactory, MusicMultiVisitable, MusicViewHolder>
typealias MusicDiffUtil = AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>
typealias MultiData = MutableList<MusicMultiVisitable>
