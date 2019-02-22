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
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.isNull
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreTrackViewModel
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.bitmap.decorateGradientMask
import com.no1.taiwan.stationmusicfm.utils.imageview.loadAnyDecorator
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import org.jetbrains.anko.support.v4.find

class ExploreTrackFragment : AdvFragment<MainActivity, ExploreTrackViewModel>() {
    companion object {
        private const val ARGUMENT_MBID = "fragment argument mbid"
        private const val ARGUMENT_ARTIST_NAME = "fragment argument artist name"
        private const val ARGUMENT_TRACK_NAME = "fragment argument track name"

        fun createBundle(mbid: String, artistName: String, trackName: String) = bundleOf(ARGUMENT_MBID to mbid,
                                                                                         ARGUMENT_ARTIST_NAME to artistName,
                                                                                         ARGUMENT_TRACK_NAME to trackName)
    }

    private val mbid by lazy { requireNotNull(arguments?.getString(ARGUMENT_MBID)) }
    private val artistName by lazy { requireNotNull(arguments?.getString(ARGUMENT_ARTIST_NAME)) }
    private val trackName by lazy { requireNotNull(arguments?.getString(ARGUMENT_TRACK_NAME)) }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.trackInfoLiveData) {
            peel { (track, similarTracks) ->
                find<TextView>(R.id.ftv_track_name).text = track.name
                find<TextView>(R.id.ftv_track_wiki).text = track.wiki.summary.parseAsHtml().toSpannable()
                track.album.images.takeIf { it.isNotEmpty() }?.let {
                    find<ImageView>(R.id.iv_track_backdrop).loadAnyDecorator(it.last().text) { bitmap, _ ->
                        val shader = LinearGradient(0f, 0f, 0f, bitmap.height.toFloat(),
                                                    Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP)
                        bitmap.decorateGradientMask(shader)
                    }
                }
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
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_track
}
