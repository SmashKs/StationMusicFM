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

package com.no1.taiwan.stationmusicfm.features.main.search

import androidx.recyclerview.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.search.viewmodels.SearchViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_SAVING_SEARCH_HIST
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.utils.presentations.peelSkipLoading
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import org.jetbrains.anko.support.v4.find
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SearchIndexFragment : IndexFragment<SearchViewModel>(), SearchCommonOperations {
    override val viewmodelProviderSource get() = PROVIDER_FROM_CUSTOM_FRAGMENT
    override val viewmodelProviderFragment get() = requireParentFragment()
    private val linearLayoutManager: () -> LinearLayoutManager by provider(ObjectLabel.LINEAR_LAYOUT_VERTICAL)
    private val adapter: MusicAdapter by instance()
    private var dropIndex = DEFAULT_INT

    init {
        BusFragLifeRegister(this)
    }

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.musics) {
            peel {
                if (it.items.isEmpty()) {
                    return@peel
                }
            } happenError {
                loge(it)
            } doWith this@SearchIndexFragment
        }
        observeNonNull(vm.histories) {
            peel {
                adapter.replaceWholeList(cast(it))
            } happenError {
                loge(it)
            } doWith this@SearchIndexFragment
        }
        observeNonNull(vm.removeRes) {
            peelSkipLoading {
                //                logw(it, dropIndex)
//                if (it && dropIndex != DEFAULT_INT)
//                    adapter.dropAt(dropIndex)
            } happenError {
                loge(it)
            } doWith this@SearchIndexFragment
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        vm.runTaskFetchHistories()
        initRecyclerViewWith(find(R.id.rv_histories), adapter, linearLayoutManager())
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_search_index

    override fun onResume() {
        super.onResume()
        parent.onQuerySubmit = { vm.runTaskAddHistory(it) }
    }

    override fun onPause() {
        super.onPause()
        parent.onQuerySubmit = null
    }

    override fun keepKeywordIntoViewModel(keyword: String) = vm.keyword.postValue(keyword)

    override fun getKeptKeyword() = vm.keyword.value.orEmpty()

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.search.viewholders.SearchHistoryViewHolder.initView]
     * @param keyword the keyword of searching.
     */
    @Subscribe(tags = [Tag(TAG_SAVING_SEARCH_HIST)])
    fun removeKeyword(keyword: String) {
        dropIndex = vm.histories.value?.data?.indexOfFirst { it.keyword == keyword } ?: DEFAULT_INT
        vm.runTaskDeleteHistory(keyword)
    }
}
