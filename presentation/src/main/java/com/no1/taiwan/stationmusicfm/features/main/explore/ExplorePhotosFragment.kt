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
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.gLaunch
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.BaseFragment
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jsoup.Jsoup
import org.kodein.di.generic.instance

class ExplorePhotosFragment : BaseFragment<MainActivity>() {
    companion object {
        private const val ARGUMENT_ARTIST_NAME = "fragment argument name"

        fun createBundle(artistName: String) = bundleOf(ARGUMENT_ARTIST_NAME to artistName)
    }

    private val linearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_HORIZONTAL)
    private val adapter: MusicAdapter by instance()
    private val name by lazy { requireNotNull(arguments?.getString(ARGUMENT_ARTIST_NAME)) }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)

        gLaunch {
            val list = parse(name)
            runOnUiThread {
                adapter.appendList(cast(list.toMutableList()))
            }
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(R.id.rv_photos, adapter, linearLayoutManager)
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_photo

    private fun parse(name: String): List<ArtistInfoEntity.PhotoEntity> {
        val page = 1

        val doc = Jsoup.connect("https://www.last.fm/music/$name/+images?page=$page").get()

        val hasNext = doc.select("li.pagination-next").select("a").toString().isNotBlank()
        val list = doc.select("ul.image-list").select("li").map {
            it.select("img.image-list-image").attr("src")
        }.map {
            it.split("/").last()
        }.map {
            ArtistInfoEntity.PhotoEntity("https://lastfm-img2.akamaized.net/i/u/770x0/$it.jpg", it)
        }

        return list
    }
}
