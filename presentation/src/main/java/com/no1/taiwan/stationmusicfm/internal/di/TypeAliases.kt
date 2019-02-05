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

package com.no1.taiwan.stationmusicfm.internal.di

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.no1.taiwan.stationmusicfm.data.data.DataMapper
import com.no1.taiwan.stationmusicfm.entities.PreziMapper

typealias ViewModelEntry = Pair<Class<out ViewModel>, ViewModel>
typealias ViewModelEntries = Set<ViewModelEntry>

typealias ViewHolderEntry = Pair<Int, Pair<Int, (View) -> RecyclerView.ViewHolder>>
typealias ViewHolderEntries = Set<ViewHolderEntry>

typealias DataMapperEntry = Pair<Class<out DataMapper>, DataMapper>
typealias DataMapperEntries = Set<DataMapperEntry>

typealias PreziMapperEntry = Pair<Class<out PreziMapper>, PreziMapper>
typealias PreziMapperEntries = Set<PreziMapperEntry>
