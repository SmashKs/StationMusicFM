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

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinshaver.cast
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.main.IndexFragment
import com.no1.taiwan.stationmusicfm.features.main.search.viewmodels.SearchViewModel
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_HISTORY
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.HistoryAdapter
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_REMOVING_SEARCH_HIST
import com.no1.taiwan.stationmusicfm.utils.RxBusConstant.Tag.TAG_SAVING_SEARCH_HIST
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.SearchShowingLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.happenError
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import com.no1.taiwan.stationmusicfm.utils.presentations.peelSkipLoading
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SearchIndexFragment : IndexFragment<SearchViewModel>(), SearchCommonOperations {
    override val viewmodelProviderSource get() = PROVIDER_FROM_CUSTOM_FRAGMENT
    override val viewmodelProviderFragment get() = requireParentFragment()
    private val linearLayoutManager: () -> LinearLayoutManager by provider(LINEAR_LAYOUT_VERTICAL)
    private val adapter: HistoryAdapter by instance(ADAPTER_HISTORY)
    private val actionBarBlankDecoration: RecyclerView.ItemDecoration by instance(
        ITEM_DECORATION_ACTION_BAR_BLANK)
    private var dropWord = DEFAULT_STR

    init {
        BusFragLifeRegister(this)
        SearchShowingLifeRegister(this)
    }

    override fun onResume() {
        super.onResume()
        parent.onQuerySubmit = { vm.runTaskAddHistory(it) }
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
                if (it && dropWord != DEFAULT_STR) {
                    adapter.removeItem(dropWord)
                }
            } happenError {
                loge(it)
            } doWith this@SearchIndexFragment
        }
    }

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    override fun rendered(savedInstanceState: Bundle?) {
        super.rendered(savedInstanceState)
        vm.runTaskFetchHistories()
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        initRecyclerViewWith(find(R.id.rv_histories),
                             adapter,
                             linearLayoutManager(),
                             actionBarBlankDecoration)
    }

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    override fun actionBarTitle() = getString(R.string.fragment_title_search_track)

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.fragment_search_index

    override fun keepKeywordIntoViewModel(keyword: String) = vm.keyword.postValue(keyword)

    override fun getKeptKeyword() = vm.keyword.value.orEmpty()

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.search.viewholders.SearchHistoryViewHolder.initView]
     * @param keyword the keyword of searching.
     */
    @Subscribe(tags = [Tag(TAG_REMOVING_SEARCH_HIST)])
    fun removeKeyword(keyword: String) {
        dropWord = keyword
        vm.runTaskDeleteHistory(keyword)
    }

    /**
     * @event_from [com.no1.taiwan.stationmusicfm.features.main.search.viewholders.SearchHistoryViewHolder.initView]
     * @param keyword the keyword of searching.
     */
    @Subscribe(tags = [Tag(TAG_SAVING_SEARCH_HIST)])
    fun searchByHistory(keyword: String) {
        parent.performKeywordSubmit(keyword)
    }
}
