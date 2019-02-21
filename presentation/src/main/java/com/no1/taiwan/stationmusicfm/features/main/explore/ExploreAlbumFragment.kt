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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreAlbumViewModel
import com.no1.taiwan.stationmusicfm.utils.bitmap.decorateGradientMask
import com.no1.taiwan.stationmusicfm.utils.imageview.loadAnyDecorator
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import org.jetbrains.anko.support.v4.find

class ExploreAlbumFragment : AdvFragment<MainActivity, ExploreAlbumViewModel>() {
    companion object {
        private const val ARGUMENT_MBID = "fragment argument mbid"
        private const val ARGUMENT_ARTIST_NAME = "fragment argument artist name"
        private const val ARGUMENT_ALBUM_NAME = "fragment argument album name"
        private const val ARGUMENT_ALBUM_THUMB_URI = "fragment argument album thumb uri"
        private const val ARGUMENT_ARTIST_THUMB_URI = "fragment argument artist thumb uri"

        fun createBundle(
            mbid: String,
            artistName: String,
            albumName: String,
            albumThumbUri: String,
            artistThumbUri: String
        ) = bundleOf(ARGUMENT_MBID to mbid,
                     ARGUMENT_ARTIST_NAME to artistName,
                     ARGUMENT_ALBUM_NAME to albumName,
                     ARGUMENT_ALBUM_THUMB_URI to albumThumbUri,
                     ARGUMENT_ARTIST_THUMB_URI to artistThumbUri)
    }

    private val mbid by lazy { requireNotNull(arguments?.getString(ARGUMENT_MBID)) }
    private val artistName by lazy { requireNotNull(arguments?.getString(ARGUMENT_ARTIST_NAME)) }
    private val albumName by lazy { requireNotNull(arguments?.getString(ARGUMENT_ALBUM_NAME)) }
    private val albumThumbUri by lazy { requireNotNull(arguments?.getString(ARGUMENT_ALBUM_THUMB_URI)) }
    private val artistThumbUri by lazy { requireNotNull(arguments?.getString(ARGUMENT_ARTIST_THUMB_URI)) }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        find<ImageView>(R.id.iv_artist_icon).loadByAny(artistThumbUri, parent)
        find<TextView>(R.id.ftv_album_name).text = albumName
        find<ImageView>(R.id.iv_album_backdrop).loadAnyDecorator(albumThumbUri) { bitmap, _ ->
            val shader = LinearGradient(0f, 0f, 0f, bitmap.height.toFloat(),
                                        Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP)
            bitmap.decorateGradientMask(shader)
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_album
}
