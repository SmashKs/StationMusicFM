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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.castOrNull
import com.devrapid.kotlinshaver.isNull
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.AnyParameters
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreAlbumViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_TRACK_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_DETAIL
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.bitmap.decorateGradientMask
import com.no1.taiwan.stationmusicfm.utils.bundle.extraNotNull
import com.no1.taiwan.stationmusicfm.utils.imageview.loadAnyDecorator
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

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

    //region Parameter
    private val mbid by extraNotNull<String>(ARGUMENT_MBID)
    private val artistName by extraNotNull<String>(ARGUMENT_ARTIST_NAME)
    private val albumName by extraNotNull<String>(ARGUMENT_ALBUM_NAME)
    private val albumThumbUri by extraNotNull<String>(ARGUMENT_ALBUM_THUMB_URI)
    private val artistThumbUri by extraNotNull<String>(ARGUMENT_ARTIST_THUMB_URI)
    //endregion
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val adapter: MusicAdapter by instance()

    init {
        BusFragLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.albumLiveData) {
            peel {
                find<TextView>(R.id.ftv_published_at).text = it.wiki.published
                find<TextView>(R.id.ftv_tags).text = it.tags.map(TagInfoEntity.TagEntity::name).joinToString("\n")
                find<TextView>(R.id.ftv_album_wiki).text = it.wiki.summary
                adapter.appendList(cast(it.tracks))
            } happenError {
                loge(it)
            } doWith this@ExploreAlbumFragment
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
            if (vm.albumLiveData.value.isNull())
                vm.runTaskFetchAlbum(mbid, albumName, artistName)
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_tracks_of_album), adapter, linearLayoutManager())
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

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreTrackViewHolder.initView]
     * @param params
     */
    @Subscribe(tags = [Tag(TAG_TO_DETAIL)])
    fun gotoTrackDetailFragment(params: AnyParameters) {
        val mbid = castOrNull<String>(params[PARAMS_COMMON_MBID]).orEmpty()
        val artistName = castOrNull<String>(params[PARAMS_COMMON_ARTIST_NAME]).orEmpty()
        val trackName = castOrNull<String>(params[PARAMS_TO_TRACK_NAME]).orEmpty()

        findNavController().navigate(R.id.action_frag_explore_album_to_frag_explore_track,
                                     ExploreTrackFragment.createBundle(mbid, artistName, trackName))
    }
}
