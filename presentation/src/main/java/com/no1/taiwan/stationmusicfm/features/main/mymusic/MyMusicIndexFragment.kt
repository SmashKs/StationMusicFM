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

package com.no1.taiwan.stationmusicfm.features.main.mymusic

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.parameters.playlist.PlaylistIndex
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewmodels.MyMusicIndexViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MyMusicIndexFragment : IndexFragment<MyMusicIndexViewModel>() {
    private val adapter: MusicAdapter by instance()
    private val linearLayoutManager: () -> LinearLayoutManager by provider(ObjectLabel.LINEAR_LAYOUT_VERTICAL)

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.playlist) {
            peel {
                logw(it)
            } happenError {
                loge(it)
            } doWith this@MyMusicIndexFragment
        }
        observeNonNull(vm.downloads) {
            logw(this)
        }
        observeNonNull(vm.favorites) {
            logw(this)
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.runTaskFetchPlaylist(listOf(PlaylistIndex.Favorite.ordinal))
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_playlist), adapter, linearLayoutManager())
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_mymusic_index
}
