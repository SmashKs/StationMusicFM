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

package com.no1.taiwan.stationmusicfm.bases

import androidx.annotation.UiThread

/**
 * Interface representing a View that will use to load data.
 */
interface LoadView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @UiThread
    fun showLoading()

    /**
     * Hide a loading view.
     */
    @UiThread
    fun hideLoading()

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    @UiThread
    fun showRetry()

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @UiThread
    fun hideRetry()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @UiThread
    fun showError(message: String)
}
