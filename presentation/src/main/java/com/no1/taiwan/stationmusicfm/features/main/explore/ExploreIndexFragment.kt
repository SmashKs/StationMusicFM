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

package com.no1.taiwan.stationmusicfm.features.main.explore

import android.os.Bundle
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.isNull
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreIndexViewModel
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel

class ExploreIndexFragment : AdvFragment<MainActivity, ExploreIndexViewModel>() {
    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.topTracks) {
            peel {
                //                logd(it.tracks)
            } happenError {
                loge(it)
            } doWith this@ExploreIndexFragment
        }
        observeNonNull(vm.topArtists) {
            peel {
                //                findNavController().navigate(R.id.action_frag_explore_index_to_frag_explore_artist,
//                                             ExploreArtistFragment.createBundle(it.artists.first().mbid))
            } happenError {
                loge(it)
            } doWith this@ExploreIndexFragment
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.apply {
            //            if (topTracks.value.isNull())
//                runTaskFetchTopTrack()
            if (topArtists.value.isNull())
                runTaskFetchTopArtist()
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_index
}
