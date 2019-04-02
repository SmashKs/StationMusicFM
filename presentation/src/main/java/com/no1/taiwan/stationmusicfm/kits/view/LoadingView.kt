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

package com.no1.taiwan.stationmusicfm.kits.view

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import com.devrapid.kotlinknifer.gone
import com.devrapid.kotlinknifer.obtainViewStub
import com.devrapid.kotlinknifer.showViewStub
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import org.jetbrains.anko.find

//region Show View Stub
inline fun Activity.showLoadingView() = showViewStub(R.id.vs_loading, R.id.v_loading, null)

inline fun Activity.showErrorView(errorMsg: String = DEFAULT_STR) = showViewStub(R.id.vs_error, R.id.v_error) {
    find<TextView>(R.id.tv_error_msg).text = errorMsg
}

inline fun Activity.showRetryView(noinline retryListener: ((View) -> Unit)? = null) =
    showViewStub(R.id.vs_retry, R.id.v_retry) { retryListener?.let(this::setOnClickListener) }
//endregion

//region Hide View Stub
fun Activity.hideViewStub(@IdRes stub: Int, @IdRes realView: Int, options: (View.() -> Unit)? = null) =
    obtainViewStub(stub, realView).apply { options?.invoke(this) }.gone()

inline fun Activity.hideLoadingView() = hideViewStub(R.id.vs_loading, R.id.v_loading)

inline fun Activity.hideErrorView() = hideViewStub(R.id.vs_error, R.id.v_error)

inline fun Activity.hideRetryView() = hideViewStub(R.id.vs_retry, R.id.v_retry).apply {
    find<Button>(R.id.btn_retry).takeIf { it.hasOnClickListeners() }?.setOnClickListener(null)
}
//endregion
