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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers

import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.consts.Pager
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables

class PagerAlbumFragment : BasePagerFragment() {
    override fun onResume() {
        super.onResume()
        if (scrollListener.fetchMoreBlock.isNull())
            scrollListener.fetchMoreBlock = ::fetchMore
    }

    override fun onDetach() {
        super.onDetach()
        // Release the function pointer.
        scrollListener.fetchMoreBlock = null
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        super.bindLiveData()
        observeNonNull(vm.albumsLiveData) {
            peel {
                // When `count` is 0 that means only the same artist searched can be appended because it doesn't
                // need to fetch again from the remote server.
                // Otherwise `count` is 1 will prevent adding again and again.
                if ((enterCount == 0 && it.albums.firstOrNull()?.artist?.name == searchArtistName) ||
                    (enterCount == 1 && it.albums.isNotEmpty() && adapter.itemCount == 0)) {
                    adapter.append(cast<MusicVisitables>(it.albums))
                }
                // After run fetch more function, [countShouldBe] will must be larger than current.
                val countShouldBe = Pager.LIMIT * it.attr.page.toInt()
                if (enterCount > 0 && it.attr.page.toInt() > 1 && countShouldBe > adapter.itemCount) {
                    adapter.append(cast<MusicVisitables>(it.albums))
                }
            } doWith this@PagerAlbumFragment
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        initRecyclerViewWith(find(R.id.rv_hot_album), adapter, girdLayoutManager())
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        super.componentListenersBinding()
        find<RecyclerView>(R.id.rv_hot_album).apply {
            clearOnScrollListeners()
            addOnScrollListener(scrollListener)
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.viewpager_album

    private fun fetchMore() {
        vm.runTaskFetchHotAlbum(searchArtistName)
    }
}
