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

import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.BaseActivity
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {
    private val navigator by lazy { findNavController(R.id.frag_nav_main) }
    private val fragmentIndexNavigator
        get() = supportFragmentManager
            .fragments.first()  // Activity's navFragment
            .childFragmentManager.fragments.first()  // DispatcherFragment
            .childFragmentManager.fragments.first()  // DispatcherFragment's navFragment
            .findNavController()
    private val bottomNavigation by lazy { find<BottomNavigationView>(R.id.bnv_navigation) }
    private val indexFragmentIds by lazy {
        listOf(R.id.frag_explore_index, R.id.frag_mymusic, R.id.frag_rank_index, R.id.frag_search_index)
    }

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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> fragmentIndexNavigator.navigateUp()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (fragmentIndexNavigator.currentDestination?.id in indexFragmentIds) {
            finish()
            return
        }
        super.onBackPressed()
    }
}
