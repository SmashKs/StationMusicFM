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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.castOrNull
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreIndexViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_DETAIL_TARGET
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_GENRE_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_TRACK_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.finally
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.kodein.di.generic.instance

class ExploreIndexFragment : IndexFragment<ExploreIndexViewModel>() {
    companion object {
        const val FRAGMENT_TARGET_ARTIST = "artist"
        const val FRAGMENT_TARGET_TRACK = "track"
        const val FRAGMENT_TARGET_GENRE = "genre"
    }

    private val trackLinearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_VERTICAL)
    private val artistLinearLayoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_HORIZONTAL)
    private val genre3GirdLayoutManager: GridLayoutManager by instance(null, 3)
    private val trackAdapter: MusicAdapter by instance()
    private val artistAdapter: MusicAdapter by instance()
    private val genreAdapter: MusicAdapter by instance()
    private var isFetchArtist = true
    private var isFetchTrack = true
    private var isFetchTag = true

    init {
        BusFragLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.topTracks) {
            peel {
                if (isFetchTrack)
                    trackAdapter.appendList(cast(it.tracks))
            } happenError {
                loge(it)
            } finally {
                isFetchTrack = false
            } doWith this@ExploreIndexFragment
        }
        observeNonNull(vm.topArtists) {
            peel {
                if (isFetchArtist)
                    artistAdapter.appendList(cast(it.artists))
            } happenError {
                loge(it)
            } finally {
                isFetchArtist = false
            } doWith this@ExploreIndexFragment
        }
        observeNonNull(vm.topTags) {
            peel {
                if (isFetchTag)
                    genreAdapter.appendList(cast(it.tags))
            } happenError {
                loge(it)
            } finally {
                isFetchTag = false
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
            if (topTracks.value.isNull())
                runTaskFetchTopTrack()
            if (topArtists.value.isNull())
                runTaskFetchTopArtist()
            if (topTags.value.isNull())
                runTaskFetchTopTag()
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(R.id.rv_tracks, trackAdapter, trackLinearLayoutManager)
        initRecyclerViewWith(R.id.rv_artists, artistAdapter, artistLinearLayoutManager)
        initRecyclerViewWith(R.id.rv_genres, genreAdapter, genre3GirdLayoutManager)
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_index

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreTrackViewHolder.initView]
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreArtistViewHolder.initView]
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreGenreViewHolder.initView]
     * @param params
     */
    @Subscribe(tags = [Tag(TAG_TO_DETAIL)])
    fun gotoNextDetailFragment(params: AnyParameters) {
        val target = cast<String>(params[PARAMS_TO_DETAIL_TARGET])
        val mbid = castOrNull<String>(params[PARAMS_COMMON_MBID]).orEmpty()
        val artistName = castOrNull<String>(params[PARAMS_COMMON_ARTIST_NAME]).orEmpty()
        val trackName = castOrNull<String>(params[PARAMS_TO_TRACK_NAME]).orEmpty()
        val genreName = castOrNull<String>(params[PARAMS_TO_GENRE_NAME]).orEmpty()

        navTo(target, mbid, artistName, trackName, genreName)
    }

    private fun navTo(target: String, mbid: String, artistName: String, trackName: String, genreName: String) {
        val (fragment, bundle) = when (target) {
            FRAGMENT_TARGET_TRACK ->
                R.id.action_frag_explore_index_to_frag_explore_track to ExploreTrackFragment.createBundle(mbid,
                                                                                                          artistName,
                                                                                                          trackName)
            FRAGMENT_TARGET_ARTIST ->
                R.id.action_frag_explore_index_to_frag_explore_artist to ExploreArtistFragment.createBundle(mbid,
                                                                                                            artistName,
                                                                                                            PROVIDER_FROM_ACTIVITY)
            FRAGMENT_TARGET_GENRE ->
                R.id.action_frag_explore_index_to_frag_explore_tag to ExploreGenreFragment.createBundle(genreName)
            else -> throw IllegalArgumentException()
        }

        findNavController().navigate(fragment, bundle)
    }
}
