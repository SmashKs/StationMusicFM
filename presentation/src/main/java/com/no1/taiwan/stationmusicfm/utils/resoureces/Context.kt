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

package com.no1.taiwan.stationmusicfm.utils.resoureces

import android.content.res.Resources
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.no1.taiwan.stationmusicfm.MusicApp
import org.jetbrains.anko.dimen

fun gContext() = MusicApp.appContext.applicationContext

fun gDimen(@DimenRes id: Int) = gContext().dimen(id)

fun gString(@StringRes id: Int) = gContext().getString(id)

fun gText(@StringRes id: Int) = gContext().getText(id)

fun gColor(@ColorRes id: Int, theme: Resources.Theme? = null) = gContext().resources.getColor(id, theme)

fun gStringArray(@ArrayRes id: Int) = gContext().resources.getStringArray(id)

fun gTypeArray(@ArrayRes id: Int) = gContext().resources.obtainTypedArray(id)

fun gResArray(@ArrayRes id: Int) =
    gStringArray(id).mapIndexed { index, _ -> index to gTypeArray(id) }.toMutableList()

fun gResArrays(@ArrayRes vararg ids: Int) =
    ids.map(::gResArray).reduce { acc, new -> acc.addAll(new);acc }
