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
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.changeColor
import com.devrapid.kotlinknifer.extraNotNull
import com.devrapid.kotlinknifer.invisible
import com.devrapid.kotlinknifer.toDrawable
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.PhotoEntity
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExplorePhotoViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_HORIZONTAL
import com.no1.taiwan.stationmusicfm.kits.recyclerview.snaphelper.SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE
import com.no1.taiwan.stationmusicfm.kits.recyclerview.snaphelper.attachSnapHelperWithListener
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchHidingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.imageview.loadDrawableIntoListener
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import com.no1.taiwan.stationmusicfm.widget.components.toast.toastX
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.findOptional
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class ExplorePhotosFragment : AdvFragment<MainActivity, ExplorePhotoViewModel>() {
    companion object {
        private const val ARGUMENT_ARTIST_NAME = "fragment argument name"
        private const val ARGUMENT_ARTIST_PHOTOS = "fragment argument photo"

        fun createBundle(artistName: String, list: List<PhotoEntity>) =
            bundleOf(ARGUMENT_ARTIST_NAME to artistName, ARGUMENT_ARTIST_PHOTOS to list)
    }

    override val backDrawable by lazy {
        R.drawable.ic_arrow_back_black.toDrawable(parent).changeColor(Color.WHITE)
    }
    //region Arguments
    private val name by extraNotNull<String>(ARGUMENT_ARTIST_NAME)
    private val preloadList by extraNotNull<ArrayList<PhotoEntity>>(ARGUMENT_ARTIST_PHOTOS)
    //endregion
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_HORIZONTAL)
    private val adapter: MusicAdapter by instance()
    private val snapHelper = LinearSnapHelper()

    init {
        SearchHidingLifeRegister(this)
    }

    override fun bindLiveData() {
        observeNonNull(vm.photosLiveData) {
            peel {
                adapter.append(cast<MusicVisitables>(it))
            } happenError {
                toastX("Unknown error happens.")
            } doWith this@ExplorePhotosFragment
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        find<ImageSwitcher>(R.id.is_backdrop).setFactory {
            ImageView(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
        // Preload from the previous fragment.
        adapter.append(cast<MusicVisitables>(preloadList))
        initRecyclerViewWith(find(R.id.rv_photos), adapter, linearLayoutManager())
        loadImageIntoSwitcher(0)
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    override fun componentListenersBinding() {
        super.componentListenersBinding()
        find<RecyclerView>(R.id.rv_photos)
            .attachSnapHelperWithListener(snapHelper, NOTIFY_ON_SCROLL_STATE_IDLE, ::loadImageIntoSwitcher)
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = "<font color='#ffffff'>$name</font>".parseAsHtml()

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_explore_photo

    override fun onBackPressed() {
        find<RecyclerView>(R.id.rv_photos).invisible()
    }

    private fun loadImageIntoSwitcher(position: Int) {
        parent.loadDrawableIntoListener(preloadList[position].url.toUri()) { resource, _ ->
            findOptional<ImageSwitcher>(R.id.is_backdrop)?.setImageDrawable(resource)
        }
    }
}
