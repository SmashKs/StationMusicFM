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
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreGenreViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance

class ExploreGenreFragment : AdvFragment<MainActivity, ExploreGenreViewModel>() {
    companion object {
        private const val ARGUMENT_TAG_NAME = "fragment argument tag name"

        fun createBundle(tagName: String) = bundleOf(ARGUMENT_TAG_NAME to tagName)
    }

    private val albumLinearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_HORIZONTAL)
    private val artistLinearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_HORIZONTAL)
    private val trackLinearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_VERTICAL)
    private val albumAdapter: MusicAdapter by instance()
    private val artistAdapter: MusicAdapter by instance()
    private val trackAdapter: MusicAdapter by instance()
    private val tagName by lazy { requireNotNull(arguments?.getString(ARGUMENT_TAG_NAME, DEFAULT_STR)) }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.tagInfoLiveData) {
            peel { (tag, artist, album, tracks) ->
                find<TextView>(R.id.ftv_genre_about).text = tag.wiki.content.parseAsHtml().toSpannable()
                albumAdapter.appendList(cast(album.albums))
                artistAdapter.appendList(cast(artist.artists))
                trackAdapter.appendList(cast(tracks.tracks))
            } happenError {
                loge(it)
            } doWith this@ExploreGenreFragment
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
            if (tagInfoLiveData.value.isNull())
                runTaskFetchGenreInfo(tagName)
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(R.id.rv_albums, albumAdapter, albumLinearLayoutManager)
        initRecyclerViewWith(R.id.rv_artists, artistAdapter, artistLinearLayoutManager)
        initRecyclerViewWith(R.id.rv_tracks, trackAdapter, trackLinearLayoutManager)
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_genre
}
