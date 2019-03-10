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
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.devrapid.kotlinknifer.changeColor
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.toDrawable
import com.devrapid.kotlinshaver.isNull
import com.google.android.material.tabs.TabLayout
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.domain.Parameters
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity.TagEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreArtistViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerAlbumFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerBioFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerSimilarArtistFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.PagerTrackFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers.SimpleFragmentPagerAdapter
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_COMMON_MBID
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_NAME
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Parameter.PARAMS_TO_ALBUM_URI
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_ALBUM
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_TO_SIMILAR_ARTIST
import com.no1.taiwan.stationmusicfm.utils.aac.data
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingByColorLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.imageview.loadByAny
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.withArguments
import org.kodein.di.generic.instance

class ExploreArtistFragment : AdvFragment<MainActivity, ExploreArtistViewModel>() {
    companion object {
        const val ARGUMENT_VM_DEPENDENT = "fragment argument view model provider source"
        const val ARGUMENT_IS_THE_SAME_ARTIST = "fragment argument is the same artist?"
        private const val ARGUMENT_MBID = "fragment argument mbid"
        private const val ARGUMENT_ARTIST_NAME = "fragment argument artist name"

        fun createBundle(mbid: String, name: String, vmProvider: Int) = bundleOf(ARGUMENT_MBID to mbid,
                                                                                 ARGUMENT_ARTIST_NAME to name,
                                                                                 ARGUMENT_VM_DEPENDENT to vmProvider)
    }

    // ViewModel's lifecycle will be itself when open a new same fragment.
    override val viewmodelProviderSource get() = vmProviderSource
    override val backDrawable by lazy {
        R.drawable.ic_arrow_back_black.toDrawable(parent).changeColor(Color.WHITE)
    }
    //region Parameter
    private val vmProviderSource by extraNotNull<Int>(ARGUMENT_VM_DEPENDENT)
    private val mbid by extraNotNull<String>(ARGUMENT_MBID)
    private val artistName by extraNotNull<String>(ARGUMENT_ARTIST_NAME)
    //endregion
    private val inflater: LayoutInflater by instance()
    private val adapterFragments by lazy {
        listOf(PagerBioFragment(), PagerAlbumFragment(), PagerTrackFragment(), PagerSimilarArtistFragment()).onEach {
            it.withArguments(ARGUMENT_VM_DEPENDENT to vmProviderSource,
                             ARGUMENT_IS_THE_SAME_ARTIST to (mbid == vm.lastFindMbid))
        }
    }

    init {
        BusFragLifeRegister(this)
        SearchShowingByColorLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.artistInfoLiveData) {
            peel { (artist, _, _, _) ->
                setArtistInfo(artist)
            } happenError {
                loge(it)
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
                mbid != vm.lastFindMbid) {
                runTaskFetchArtistInfo(mbid, artistName)
                // Pre-load the photos.
                runTaskFetchArtistPhoto(artistName)
            }
            else {
                setArtistInfo(requireNotNull(artistInfoLiveData.data()?.first))
            }
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
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        super.componentListenersBinding()
        find<ImageView>(R.id.iv_artist_backdrop).setOnClickListener {
            findNavController().navigate(R.id.action_frag_explore_artist_to_frag_explore_photo,
                                         ExplorePhotosFragment.createBundle(vm.artistLiveData.data()?.name.orEmpty(),
                                                                            vm.photosLiveData.data()?.photos.orEmpty()))
        }
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = "<font color='#ffffff'>$artistName</font>".parseAsHtml()

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_artist

    private fun setArtistInfo(artist: ArtistEntity) {
        find<ImageView>(R.id.iv_artist_backdrop).loadByAny(artist.images.last().text)
        find<ImageView>(R.id.iv_artist_thumbnail).loadByAny(artist.images.last().text)
        find<TextView>(R.id.ftv_tags).text = artist.tags.joinToString("\n", transform = TagEntity::name)
        find<TextView>(R.id.ftv_mics).text = artist.listeners
    }

    private fun getTabView(position: Int) = inflater.inflate(R.layout.tabitem_introduction, null).apply {
        this.find<TextView>(R.id.ftv_title).text = resources.getStringArray(R.array.artist_detail_column)[position]
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.SimilarArtistViewHolder.initView]
     * @param params AnyParameters
     */
    @Subscribe(tags = [Tag(TAG_TO_SIMILAR_ARTIST)])
    fun navToNextOfMe(params: Parameters) {
        val name = requireNotNull(params[PARAMS_COMMON_ARTIST_NAME])
        val mbid = requireNotNull(params[PARAMS_COMMON_MBID])

        findNavController().navigate(R.id.action_frag_explore_artist_self,
                                     ExploreArtistFragment.createBundle(mbid, name, PROVIDER_FROM_CUSTOM_FRAGMENT))
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotAlbumViewHolder.initView]
     * @param params Parameters
     */
    @Subscribe(tags = [Tag(TAG_TO_ALBUM)])
    fun navToAlbumDetail(params: Parameters) {
        val mbid = requireNotNull(params[PARAMS_COMMON_MBID])
        val albumName = requireNotNull(params[PARAMS_TO_ALBUM_NAME])
        val albumUri = requireNotNull(params[PARAMS_TO_ALBUM_URI])
        val artistName = requireNotNull(params[PARAMS_COMMON_ARTIST_NAME])

        findNavController().navigate(R.id.action_frag_explore_artist_to_frag_explore_album,
                                     ExploreAlbumFragment.createBundle(mbid,
                                                                       artistName,
                                                                       albumName,
                                                                       albumUri,
                                                                       vm.artistLiveData.data()?.images?.last()?.text.orEmpty()))
    }
}
