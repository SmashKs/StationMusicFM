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

package com.no1.taiwan.stationmusicfm.utils.aac

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.devrapid.kotlinknifer.WeakRef
import com.hwangjr.rxbus.RxBus

class BusFragLifeRegister(fragment: Fragment) : LifecycleObserver {
    private val frag by WeakRef(fragment)

    init {
        frag?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerRxBus() {
        frag?.run(RxBus.get()::register)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unregisterRxBus() {
        frag?.run(RxBus.get()::unregister)
    }
}

class BusActLifeRegister(activity: AppCompatActivity) : LifecycleObserver {
    private val act by WeakRef(activity)

    init {
        act?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerRxBus() {
        act?.run(RxBus.get()::register)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unregisterRxBus() {
        act?.run(RxBus.get()::unregister)
    }
}

class BusFragLongerLifeRegister(fragment: Fragment) : LifecycleObserver {
    private val frag by WeakRef(fragment)

    init {
        frag?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun registerRxBus() {
        frag?.run(RxBus.get()::register)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterRxBus() {
        frag?.run(RxBus.get()::unregister)
    }
}

class BusActLongerLifeRegister(activity: AppCompatActivity) : LifecycleObserver {
    private val act by WeakRef(activity)

    init {
        act?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun registerRxBus() {
        act?.run(RxBus.get()::register)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterRxBus() {
        act?.run(RxBus.get()::unregister)
    }
}