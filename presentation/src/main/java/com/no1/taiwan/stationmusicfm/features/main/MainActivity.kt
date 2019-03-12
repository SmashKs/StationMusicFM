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

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinshaver.cast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvFragment.Companion.PROVIDER_FROM_ACTIVITY
import com.no1.taiwan.stationmusicfm.bases.BaseActivity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreArtistFragment
import com.no1.taiwan.stationmusicfm.features.main.explore.ExploreGenreFragment
import com.no1.taiwan.stationmusicfm.features.main.search.SearchCommonOperations
import com.no1.taiwan.stationmusicfm.features.main.search.SearchIndexFragment
import com.no1.taiwan.stationmusicfm.features.main.search.SearchResultFragment
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {
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
        listOf(R.id.frag_explore_index, R.id.frag_mymusic, R.id.frag_rank_index, R.id.frag_search_index)
    }
    var searchItem: MenuItem? = null
    var onQuerySubmit by WeakRef<(query: String) -> Unit>()

//    private lateinit var player: ExoPlayerWrapper
//    private val permissionsStorage = arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
//    private val permissionsRequestCode = 1
//    private val url =
//        "https://soundsthatmatterblog.files.wordpress.com/2012/12/04-just-give-me-a-reason-feat-nate-ruess.mp3"
//
//    private fun requirePermission() {
//        ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
//            .takeIf { it == PackageManager.PERMISSION_DENIED }
//            ?.let { ActivityCompat.requestPermissions(this, permissionsStorage, permissionsRequestCode) }
//    }
//
//    fun playASong() {
//        player = ExoPlayerWrapper(application)
//        player.play(url)
//    }

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

                // Reassign to bottom navigation view.
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
        fragmentIndexNavigator.navigateUp()
    }

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
                                                                                   PROVIDER_FROM_ACTIVITY))
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

    private fun searchViewSetting() {
        cast<SearchView>(searchItem?.actionView).apply {
            queryHint = "a keyword of artist, album, tracks..."
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
