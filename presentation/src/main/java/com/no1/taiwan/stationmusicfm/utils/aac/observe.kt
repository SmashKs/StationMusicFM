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

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.no1.taiwan.stationmusicfm.domain.ResponseState

/**
 * Observe the [LiveData]'s nullable value from [androidx.lifecycle.ViewModel].
 */
inline fun <reified T> Fragment.observe(liveData: LiveData<T>, noinline block: (T?.() -> Unit)? = null) =
    block?.let { liveData.observe(viewLifecycleOwner, Observer(it)) }

/**
 * Observe the [LiveData]'s nonnull value from [androidx.lifecycle.ViewModel].
 */
inline fun <reified T> Fragment.observeNonNull(liveData: LiveData<T>, noinline block: (T.() -> Unit)? = null) =
    block?.run { liveData.observe(viewLifecycleOwner, Observer { it?.let(this) }) }

/**
 * Observe the [LiveData]'s nullable value which comes from the un-boxed [ResponseState] value
 * from [androidx.lifecycle.ViewModel].
 */
inline fun <reified E, T : ResponseState<E>> Fragment.observeUnbox(
    liveData: LiveData<T>,
    noinline block: (E?.() -> Unit)? = null
) = block?.run { liveData.observe(viewLifecycleOwner, Observer { it?.data.let(this) }) }

/**
 * Observe the [LiveData]'s nonnull value which comes from the un-boxed [ResponseState] value
 * from [androidx.lifecycle.ViewModel].
 */
inline fun <reified E, T : ResponseState<E>> Fragment.observeUnboxNonNull(
    liveData: LiveData<T>,
    noinline block: (E.() -> Unit)? = null
) = block?.run { liveData.observe(viewLifecycleOwner, Observer { it?.data?.let(block) }) }

/**
 * Observe the [LiveData]'s nullable value from [androidx.lifecycle.ViewModel].
 */
inline fun <reified T> LifecycleOwner.observe(liveData: LiveData<T>, noinline block: (T?.() -> Unit)? = null) =
    block?.let { liveData.observe(this, Observer(it)) }

/**
 * Observe the [LiveData]'s nonnull value from [androidx.lifecycle.ViewModel].
 */
inline fun <reified T> LifecycleOwner.observeNonNull(liveData: LiveData<T>, noinline block: (T.() -> Unit)? = null) =
    block?.run { liveData.observe(this@observeNonNull, Observer { it?.let(this) }) }

/**
 * Observe the [LiveData]'s nullable value which comes from the un-boxed [ResponseState] value
 * from [androidx.lifecycle.ViewModel].
 */
inline fun <reified E, T : ResponseState<E>> LifecycleOwner.observeUnbox(
    liveData: LiveData<T>,
    noinline block: (E?.() -> Unit)? = null
) = block?.run { liveData.observe(this@observeUnbox, Observer { it?.data.let(this) }) }

/**
 * Observe the [LiveData]'s nonnull value which comes from the un-boxed [ResponseState] value
 * from [androidx.lifecycle.ViewModel].
 */
inline fun <reified E, T : ResponseState<E>> LifecycleOwner.observeUnboxNonNull(
    liveData: LiveData<T>,
    noinline block: (E.() -> Unit)? = null
) = block?.run { liveData.observe(this@observeUnboxNonNull, Observer { it?.data?.let(block) }) }
