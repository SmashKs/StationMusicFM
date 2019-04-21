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

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.text.toSpannable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.decorateGradientMask
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
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreTrackViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.data
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchHidingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.imageview.loadAnyDecorator
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.utils.textview.setHighlightLink
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class ExploreTrackFragment : AdvFragment<MainActivity, ExploreTrackViewModel>() {
    companion object {
        private const val ARGUMENT_MBID = "fragment argument mbid"
        private const val ARGUMENT_ARTIST_NAME = "fragment argument artist name"
        private const val ARGUMENT_TRACK_NAME = "fragment argument track name"

        fun createBundle(mbid: String, artistName: String, trackName: String) = bundleOf(ARGUMENT_MBID to mbid,
                                                                                         ARGUMENT_ARTIST_NAME to artistName,
                                                                                         ARGUMENT_TRACK_NAME to trackName)
    }

    //region Arguments
    private val mbid by extraNotNull<String>(ARGUMENT_MBID)
    private val artistName by extraNotNull<String>(ARGUMENT_ARTIST_NAME)
    private val trackName by extraNotNull<String>(ARGUMENT_TRACK_NAME)
    //endregion
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val adapter: MusicAdapter by instance()

    init {
        BusFragLifeRegister(this)
        SearchHidingLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.trackInfoLiveData) {
            peel { (track, similarTracks) ->
                firstTimeEnter = false
                displayInformation(track)
                adapter.append(cast<MusicVisitables>(similarTracks.tracks))
            } happenError {
                loge(it)
            } doWith this@ExploreTrackFragment
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
            if (trackInfoLiveData.value.isNull())
                runTaskFetchTrack(mbid, artistName, trackName)
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_similar_tracks), adapter, linearLayoutManager())
        if (!firstTimeEnter) {
            vm.trackInfoLiveData.data()?.first?.let(::displayInformation)
        }
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = trackName

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_track

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreTrackViewHolder.initView]
     * @param params
     */
    @Subscribe(tags = [Tag(TAG_TO_DETAIL)])
    fun gotoSelf(params: AnyParameters) {
        val mbid = castOrNull<String>(params[RxBusConstant.Parameter.PARAMS_COMMON_MBID]).orEmpty()
        val artistName = castOrNull<String>(params[RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME]).orEmpty()
        val trackName = castOrNull<String>(params[RxBusConstant.Parameter.PARAMS_TO_TRACK_NAME]).orEmpty()
        findNavController().navigate(R.id.action_frag_explore_track_self,
                                     createBundle(mbid, artistName, trackName))
    }

    private fun displayInformation(track: TrackInfoEntity.TrackEntity) {
        find<TextView>(R.id.ftv_track_name).text = track.name
        find<TextView>(R.id.ftv_track_wiki).apply {
            text = track.wiki.summary.parseAsHtml().toSpannable()
            setHighlightLink()
        }
        track.album.images.takeIf { it.isNotEmpty() }?.let {
            find<ImageView>(R.id.iv_track_backdrop).loadAnyDecorator(it.last().text) { bitmap, _ ->
                val shader = LinearGradient(0f, 0f, 0f, bitmap.height.toFloat(),
                                            Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP)
                bitmap.decorateGradientMask(shader)
            }
        }
    }
}
