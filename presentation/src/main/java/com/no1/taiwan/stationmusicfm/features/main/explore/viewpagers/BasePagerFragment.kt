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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.extraNotNull
import com.no1.taiwan.stationmusicfm.bases.AdvFragment
import com.no1.taiwan.stationmusicfm.features.main.MainActivity
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreArtistFragment.Companion.ARGUMENT_ARTIST_NAME
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreArtistFragment.Companion.ARGUMENT_VM_DEPENDENT
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreArtistViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.NotifiableAdapter
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

abstract class BasePagerFragment : AdvFragment<MainActivity, ExploreArtistViewModel>() {
    // Follow the parent fragment's viewmodel.
    override val viewmodelProviderSource get() = vmProviderSource
    // Always get parent fragment's lifecycle for providing a viewmodel.
    override val viewmodelProviderFragment get() = requireParentFragment()

    protected val girdLayoutManager: () -> GridLayoutManager by provider(null, 2)
    protected val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    protected val adapter: NotifiableAdapter by instance(ADAPTER_TRACK)
    protected var enterCount = 0
    //region Parameter
    private val vmProviderSource by extraNotNull<Int>(ARGUMENT_VM_DEPENDENT)
    protected val searchArtistName by extraNotNull<String>(ARGUMENT_ARTIST_NAME)
    //endregion

    override fun onResume() {
        super.onResume()
        enterCount++
    }
}
