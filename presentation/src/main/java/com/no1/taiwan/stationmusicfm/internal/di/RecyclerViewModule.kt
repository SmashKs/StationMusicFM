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

import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_KEYWORD
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.ViewHolderEntry
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.utils.MusicDiffUtil
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

/**
 * To provide the necessary objects into the recycler view.
 */
object RecyclerViewModule {
    fun recyclerViewProvider() = Module("Recycler View") {
        import(adapterProvider())
        import(diffUtilProvider())
        import(mappingProvider())
        import(viewHolderProvider())
    }

    private fun mappingProvider() = Module("View Holder Mapping") {
        /** ViewModel Set for [MultiTypeFactory] */
        bind() from setBinding<ViewHolderEntry>()
    }

    private fun adapterProvider() = Module("Recycler View Adapter") {
        bind<MultiTypeFactory>() with singleton {
            MultiTypeFactory(instance())
        }
    }

    private fun diffUtilProvider() = Module("Recycler View DiffUtil") {
        bind<MusicDiffUtil>(UTIL_DIFF_KEYWORD) with instance(MusicDiffUtil())
    }

    private fun viewHolderProvider() = Module("Viewholder Module") {
        // *** ViewHolder
    }
}
