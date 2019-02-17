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
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.viewpager.widget.ViewPager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.isNull
import com.google.android.material.tabs.TabLayout
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreArtistViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerAlbumFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerSimilarArtistFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerTrackFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.SimpleFragmentPagerAdapter
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find

class ExploreArtistFragment : AdvFragment<MainActivity, ExploreArtistViewModel>() {
    companion object {
        private const val ARGUMENT_MBID = "fragment argument mbid"

        fun createBundle(mbid: String) = bundleOf(ARGUMENT_MBID to mbid)
    }

    override val viewmodelProviderSource get() = PROVIDER_FROM_ACTIVITY
    private val adapterFragments by lazy {
        listOf(PagerAlbumFragment(),
               PagerTrackFragment(),
               PagerSimilarArtistFragment())
    }
    private val mbid by lazy { requireNotNull(arguments?.getString(ARGUMENT_MBID, DEFAULT_STR)) }
    private var isFirstTime = true

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.artistInfoLiveData) {
            peel { (artist, album, artists, tracks) ->
                find<ImageView>(R.id.iv_artist_backdrop).loadByAny(artist.images.last().text)
                find<ImageView>(R.id.iv_artist_thumbnail).loadByAny(artist.images.last().text)
                find<TextView>(R.id.ftv_artist_name).text = artist.name
                find<TextView>(R.id.ftv_tags).text = artist.tags.map { it.name }.joinToString("\n")
            } happenError {
                loge(it)
            } happenError {
                isFirstTime = false
            } doWith this@ExploreArtistFragment
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
            // 1. `artistInfoLiveData.value.isNull()` is for avoiding the back fragment again and search it.
            if (artistInfoLiveData.value.isNull() ||
                // 2. `mbid != vm.lastFindMbid` is for avoiding searching the same artist.
                // 3. `isFirstTime` is for the first time open this fragment.
                (mbid != vm.lastFindMbid && isFirstTime))
                runTaskFetchArtistInfo(mbid)
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        find<ViewPager>(R.id.vp_container).also {
            it.adapter = SimpleFragmentPagerAdapter(childFragmentManager, adapterFragments)
            find<TabLayout>(R.id.tl_category).apply {
                setupWithViewPager(it)
                repeat(tabCount) { getTabAt(it)?.customView = getTabView(it) }
            }
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_artist

    private fun getTabView(position: Int) = LayoutInflater.from(parent)
        .inflate(R.layout.tabitem_introduction, null)
        .apply {
            this.find<TextView>(R.id.ftv_title).text = resources.getStringArray(R.array.artist_detail_column)[position]
        }
}
