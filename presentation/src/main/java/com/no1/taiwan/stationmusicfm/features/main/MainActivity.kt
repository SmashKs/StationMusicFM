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

package com.no1.taiwan.stationmusicfm.features.main

import android.text.Spannable
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.devrapid.kotlinknifer.SoftRef
import com.devrapid.kotlinknifer.changeColor
import com.devrapid.kotlinknifer.toBitmap
import com.devrapid.kotlinknifer.toDrawable
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.castOrNull
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment.Companion.PROVIDER_FROM_CUSTOM_FRAGMENT
import com.no1.taiwan.stationmusicfm.bases.BaseActivity
import com.no1.taiwan.stationmusicfm.bases.BaseFragment
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreArtistFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreGenreFragment
import com.no1.taiwan.stationmusicfm.features.main.search.SearchCommonOperations
import com.no1.taiwan.stationmusicfm.features.main.search.SearchIndexFragment
import com.no1.taiwan.stationmusicfm.features.main.search.SearchResultFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.find

open class MainActivity : BaseActivity() {
    private val navigator by lazy { findNavController(R.id.frag_nav_main) }
    private val currentFragment
        get() = supportFragmentManager
            .fragments.first()  // Activity's navFragment
            .childFragmentManager.fragments.first()  // Dispatcher Fragment
            .childFragmentManager.fragments.first()  // Dispatcher Fragment's navFragment
            .childFragmentManager.fragments.first()  // Current Fragment
    private val fragmentIndexNavigator get() = currentFragment.findNavController()
    private val bottomNavigation by lazy { find<BottomNavigationView>(R.id.bnv_navigation) }
    private val indexFragmentIds by lazy {
        listOf(R.id.frag_explore_index, R.id.frag_music_index, R.id.frag_rank_index, R.id.frag_search_index)
    }
    var searchItem: MenuItem? = null
    var onQuerySubmit by SoftRef<(query: String) -> Unit>()

    /**
     * For separating the huge function code in [init]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        super.viewComponentBinding()
        // Set the listener of selecting a menu item.
        bottomNavigation.apply {
            setupWithNavController(navigator)
            setOnNavigationItemSelectedListener {
                // Avoid the reselect.
                if (it.itemId == selectedItemId) return@setOnNavigationItemSelectedListener false
                // Pop all fragments from the back stack.
                while (navigator.popBackStack());
                // Close the search view bar.
                searchItem?.collapseActionView()
                // Reassign to bottom navigation view.
                // If we wanna use animation when changing to other navigation page.
                NavigationUI.onNavDestinationSelected(it, navigator)
            }
        }
    }

    override fun provideLayoutId() = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        searchItem = menu.findItem(R.id.menu_search)
        searchItem?.isVisible = true
        searchViewSetting()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> fragmentIndexNavigator.navigateUp()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (fragmentIndexNavigator.currentDestination?.id in indexFragmentIds) {
            finish()
            return
        }
        castOrNull<BaseFragment<MainActivity>>(currentFragment)?.onBackPressed()
        fragmentIndexNavigator.navigateUp()
    }

    //region Search a music functions
    fun performKeywordSubmit(query: String) {
        onQuerySubmit?.invoke(query)
        searchItem?.collapseActionView()

        when (currentFragment) {
            is SearchIndexFragment -> fragmentIndexNavigator.navigate(R.id.action_frag_search_index_to_frag_search_result)
            is SearchResultFragment -> cast<SearchResultFragment>(currentFragment).searchMusicBy(query)
            is ExploreArtistFragment -> {
                fragmentIndexNavigator.navigate(R.id.action_frag_explore_artist_self,
                                                ExploreArtistFragment.createBundle(DEFAULT_STR,
                                                                                   query,
                                                                                   PROVIDER_FROM_CUSTOM_FRAGMENT))
            }
            is ExploreGenreFragment -> {
                fragmentIndexNavigator.navigate(R.id.action_frag_explore_tag_self,
                                                ExploreGenreFragment.createBundle(query))
            }
        }
    }

    fun showSearchButton() {
        searchItem?.isVisible = true
    }

    fun hideSearchButton() {
        searchItem?.isVisible = false
    }
    //endregion

    private fun searchViewSetting() {
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            /**
             * Called when a menu item with [MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW]
             * is expanded.
             * @param item Item that was expanded
             * @return true if the item should expand, false if expansion should be suppressed.
             */
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                val (iconColorRes, textColorRes, hint) = when (currentFragment) {
                    is ExploreArtistFragment -> Triple(android.R.color.white,
                                                       R.color.alto_alpha_80,
                                                       "   a keyword of an artist...")
                    else -> Triple(R.color.colorPrimaryDarkV1,
                                   android.R.color.darker_gray,
                                   "   a keyword of artist, album, tracks...")
                }
                val iconColor = ContextCompat.getColor(this@MainActivity, iconColorRes)
                val textColor = ContextCompat.getColor(this@MainActivity, textColorRes)
                val backIcon = R.drawable.ic_arrow_back_black
                    .toDrawable(this@MainActivity)
                    .changeColor(iconColor)
                val closeIcon = R.drawable.ic_close_black
                    .toDrawable(this@MainActivity)
                    .changeColor(iconColor)
                val magnifierIcon = R.drawable.ic_search_black
                    .toDrawable(this@MainActivity)
                    .changeColor(textColor)
                // Because delay to find back icon. If in the same UI thread, it's impossible to aware [Toolbar]'s
                // layout changes.
                CoroutineScope(Dispatchers.Main).launch {
                    cast<ImageButton>(this@MainActivity.find<Toolbar>(R.id.tb_header).children.toList()[1]).apply {
                        setImageDrawable(backIcon)
                    }
                    cast<SearchView>(item.actionView).apply {
                        find<ImageView>(R.id.search_close_btn).setImageDrawable(closeIcon)
                        find<AutoCompleteTextView>(R.id.search_src_text).also {
                            it.setTextColor(iconColor)
                            it.setHintTextColor(textColor)
                            it.hint = hint.toSpannable().apply {
                                setSpan(ImageSpan(this@MainActivity, magnifierIcon.toBitmap()),
                                        1,
                                        2,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            }
                        }
                    }
                }

                return true
            }

            /**
             * Called when a menu item with [MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW]
             * is collapsed.
             * @param item Item that was collapsed
             * @return true if the item should collapse, false if collapsing should be suppressed.
             */
            override fun onMenuItemActionCollapse(item: MenuItem) = true
        })
        cast<SearchView>(searchItem?.actionView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    performKeywordSubmit(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isBlank()) return true
                    (currentFragment as? SearchCommonOperations)?.keepKeywordIntoViewModel(newText)
                    return true
                }
            })
        }
    }
}
