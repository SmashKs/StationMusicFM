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

package com.no1.taiwan.stationmusicfm.ktx.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.Coil
import coil.api.load

fun ImageView.load(str: String, context: Context? = null) =
    load(str) { allowHardware(false) }

fun ImageView.load(bitmap: Bitmap, context: Context? = null) =
    load(bitmap) { allowHardware(false) }

fun ImageView.load(uri: Uri, context: Context? = null) =
    load(uri) { allowHardware(false) }

fun ImageView.load(drawable: Drawable, context: Context? = null) =
    load(drawable) { allowHardware(false) }

fun ImageView.load(@DrawableRes resource: Int, context: Context? = null) =
    load(resource) { allowHardware(false) }

fun ImageView.loadStrDecorator(
    uri: String,
    context: Context,
    decorate: ((drawable: Drawable) -> Bitmap)? = null
) = Coil.load(context, uri) {
    target {
        val bitmap = decorate?.invoke(it)
        bitmap?.let { this@loadStrDecorator.load(it) }
    }
    allowHardware(false)
}
