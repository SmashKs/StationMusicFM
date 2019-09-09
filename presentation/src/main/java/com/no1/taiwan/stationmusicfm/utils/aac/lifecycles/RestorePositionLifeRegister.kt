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

package com.no1.taiwan.stationmusicfm.utils.aac.lifecycles

import androidx.annotation.IdRes
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.no1.taiwan.stationmusicfm.ktx.aac.lifecycle.FragmentLifeRegister
import com.no1.taiwan.stationmusicfm.ktx.view.find

class RestorePositionLifeRegister(
    fragment: Fragment,
    @field:IdRes private val scrollViewId: Int
) : FragmentLifeRegister<Fragment>(fragment) {
    private var posY = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun listenScrollViewPositions() {
        frag?.find<NestedScrollView>(scrollViewId)
            ?.setOnScrollChangeListener { _: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
                posY = scrollY
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun restoreLastTimePosY() {
        // Restore the position Y of [NestedScrollView].
        frag?.find<NestedScrollView>(scrollViewId)?.scrollY = posY
    }
}
