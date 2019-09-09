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

package com.no1.taiwan.stationmusicfm.widget.components.toast

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.no1.taiwan.stationmusicfm.widget.R

fun Context.toastX(msg: String = "") = buildCustomToast(msg)

fun Fragment.toastX(msg: String = "") = requireContext().buildCustomToast(msg)

fun Activity.toastX(msg: String) = buildCustomToast(msg)

fun Context.toastLongX(msg: String = "") = buildCustomToast(msg, Toast.LENGTH_LONG)

fun Fragment.toastLongX(msg: String = "") =
    requireContext().buildCustomToast(msg, Toast.LENGTH_LONG)

fun Activity.toastLongX(msg: String) = buildCustomToast(msg, Toast.LENGTH_LONG)

private fun Context.buildCustomToast(msg: String, durationTime: Int = Toast.LENGTH_SHORT) = run {
    val layout = LayoutInflater.from(this).inflate(R.layout.part_toast, null).apply {
        findViewById<TextView>(R.id.ftv_toast_msg).apply { text = msg }
    }

    Toast(this).apply {
        setGravity(Gravity.BOTTOM, 0, resources?.getDimension(R.dimen.md_two_unit)?.toInt() ?: 0)
        duration = durationTime
        view = layout
    }
}.apply { show() }
