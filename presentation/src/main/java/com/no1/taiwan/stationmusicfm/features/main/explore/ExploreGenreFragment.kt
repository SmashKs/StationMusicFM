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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.castOrNull
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.domain.Parameters
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreGenreViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_URI
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_TRACK_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_ALBUM
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.data
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class ExploreGenreFragment : AdvFragment<MainActivity, ExploreGenreViewModel>() {
    companion object {
        private const val ARGUMENT_TAG_NAME = "fragment argument tag name"

        fun createBundle(tagName: String) = bundleOf(ARGUMENT_TAG_NAME to tagName)
    }

    private val verLinearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val horLinearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_HORIZONTAL)
    private val albumAdapter: MusicAdapter by instance()
    private val artistAdapter: MusicAdapter by instance()
    private val trackAdapter: MusicAdapter by instance()
    private val tagName by extraNotNull<String>(ARGUMENT_TAG_NAME)

    init {
        BusFragLifeRegister(this)
        SearchShowingLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.tagInfoLiveData) {
            peel { (tag, artist, album, tracks) ->
                find<TextView>(R.id.ftv_genre_about).text = tag.wiki.content.parseAsHtml().toSpannable()
                albumAdapter.append(cast<MusicVisitables>(album.albums))
                artistAdapter.append(cast<MusicVisitables>(artist.artists))
                trackAdapter.append(cast<MusicVisitables>(tracks.tracks))
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
        initRecyclerViewWith(find(R.id.rv_albums), albumAdapter, horLinearLayoutManager())
        initRecyclerViewWith(find(R.id.rv_artists), artistAdapter, horLinearLayoutManager())
        initRecyclerViewWith(find(R.id.rv_tracks), trackAdapter, verLinearLayoutManager())
        // For coming back here and show it again.
        if (vm.tagInfoLiveData.value != null)
            find<TextView>(R.id.ftv_genre_about).text =
                vm.tagInfoLiveData.data()?.first?.wiki?.content?.parseAsHtml()?.toSpannable()
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = tagName

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_genre

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.AlbumOfGenreViewHolder.initView]
     * @param params Parameters
     */
    @Subscribe(tags = [Tag(TAG_TO_ALBUM)])
    fun navToAlbumDetail(params: Parameters) {
        val mbid = requireNotNull(params[PARAMS_COMMON_MBID])
        val albumName = requireNotNull(params[PARAMS_TO_ALBUM_NAME])
        val albumUri = requireNotNull(params[PARAMS_TO_ALBUM_URI])
        val artistName = requireNotNull(params[PARAMS_COMMON_ARTIST_NAME])
        val artistThumbUri = requireNotNull(params["uri"])

        findNavController().navigate(R.id.action_frag_explore_tag_to_frag_explore_album,
                                     ExploreAlbumFragment.createBundle(mbid,
                                                                       artistName,
                                                                       albumName,
                                                                       albumUri,
                                                                       artistThumbUri))
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.TrackOfGenreViewHolder.initView]
     * @param params
     */
    @Subscribe(tags = [Tag(TAG_TO_DETAIL)])
    fun gotoTrackDetailFragment(params: AnyParameters) {
        val mbid = castOrNull<String>(params[PARAMS_COMMON_MBID]).orEmpty()
        val artistName = castOrNull<String>(params[PARAMS_COMMON_ARTIST_NAME]).orEmpty()
        val trackName = castOrNull<String>(params[PARAMS_TO_TRACK_NAME]).orEmpty()

        findNavController().navigate(R.id.action_frag_explore_tag_to_frag_explore_track,
                                     ExploreTrackFragment.createBundle(mbid, artistName, trackName))
    }
}
