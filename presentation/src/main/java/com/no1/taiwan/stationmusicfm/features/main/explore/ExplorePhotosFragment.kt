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

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.BaseFragment
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.kodein.di.generic.instance

class ExplorePhotosFragment : BaseFragment<MainActivity>() {
    companion object {
        private const val ARGUMENT_ARTIST_NAME = "fragment argument name"
        private const val ARGUMENT_ARTIST_PHOTOS = "fragment argument photo"

        fun createBundle(artistName: String, list: List<ArtistInfoEntity.PhotoEntity>) =
            bundleOf(ARGUMENT_ARTIST_NAME to artistName,
                     ARGUMENT_ARTIST_PHOTOS to list)
    }

    private val linearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_HORIZONTAL)
    private val adapter: MusicAdapter by instance()
    private val name by lazy { requireNotNull(arguments?.getString(ARGUMENT_ARTIST_NAME)) }
    private val preloadList by lazy {
        requireNotNull(arguments?.getParcelableArrayList<ArtistInfoEntity.PhotoEntity>(ARGUMENT_ARTIST_PHOTOS))
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        // Preload from the previous fragment.
        adapter.appendList(preloadList.toMutableList())
        initRecyclerViewWith(R.id.rv_photos, adapter, linearLayoutManager)
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_photo
}
