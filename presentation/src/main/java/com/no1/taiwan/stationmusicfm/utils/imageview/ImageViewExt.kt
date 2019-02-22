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

package com.no1.taiwan.stationmusicfm.utils.imageview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.utils.resoureces.gContext

@GlideModule
class MusicGlideModule : AppGlideModule()

fun ImageView.loadByString(str: String, context: Context = gContext()) =
    glideTemplate(context) { load(str) }

fun ImageView.loadByBitmap(bitmap: Bitmap, context: Context = gContext()) =
    glideTemplate(context) { load(bitmap) }

fun ImageView.loadByUri(uri: Uri, context: Context = gContext()) =
    glideTemplate(context) { load(uri) }

fun ImageView.loadByDrawable(drawable: Drawable, context: Context = gContext()) =
    glideTemplate(context) { load(drawable) }

fun ImageView.loadByAny(any: Any, context: Context = gContext()) =
    glideTemplate(context) { load(any) }

fun glideMusicOptions(
    @DrawableRes phResource: Int = R.drawable.placeholder,
    @DrawableRes erSource: Int = R.drawable.placeholder
) =
    RequestOptions().apply {
        centerCrop()
        placeholder(phResource)
        error(erSource)
        priority(Priority.HIGH)
        diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    }

fun ImageView.glideTemplate(
    context: Context = gContext(),
    block: RequestBuilder<Bitmap>.() -> RequestBuilder<Bitmap>
) = Glide.with(context)
    .asBitmap()
    .apply(glideMusicOptions())
    .block()
    .into(this)

fun ImageView.loadAnyByInternetListener(
    uri: Any,
    context: Context = gContext(),
    failBlock: ((errorDrawable: Drawable?) -> Unit)? = null,
    clearedBlock: ((placeholder: Drawable?) -> Unit)? = null,
    readyBlock: ((resource: Bitmap, transition: Transition<in Bitmap>?) -> Unit)? = null
) = Glide.with(context)
    .asBitmap()
    .load(uri)
    .apply(glideMusicOptions())
    .into(object : CustomViewTarget<ImageView, Bitmap>(this) {
        override fun onLoadFailed(errorDrawable: Drawable?) {
            failBlock?.invoke(errorDrawable)
        }

        override fun onResourceCleared(placeholder: Drawable?) {
            clearedBlock?.invoke(placeholder)
        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            readyBlock?.invoke(resource, transition)
        }
    })

fun ImageView.loadAnyDecorator(
    uri: Any,
    context: Context = gContext(),
    afterDecorated: ((bitmap: Bitmap, transition: Transition<in Bitmap>?) -> Unit)? = null,
    beforeDecorate: ((bitmap: Bitmap, transition: Transition<in Bitmap>?) -> Bitmap)? = null
) = Glide.with(context)
    .asBitmap()
    .load(uri)
    .apply(glideMusicOptions())
    .into(object : BitmapImageViewTarget(this) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            val decorated = beforeDecorate?.invoke(resource, transition)
            super.onResourceReady(decorated ?: resource, transition)
            afterDecorated?.invoke(resource, transition)
        }
    })

fun ImageView.loadAnyWithListener(
    uri: Any,
    context: Context = gContext(),
    failBlock: ((e: GlideException?) -> Unit)? = null,
    readyBlock: ((resource: Bitmap?) -> Unit)? = null
) = Glide.with(context)
    .asBitmap()
    .load(uri)
    .apply(glideMusicOptions())
    .listener(object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            failBlock?.invoke(e)
            return false
        }

        override fun onResourceReady(
            resource: Bitmap,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            readyBlock?.invoke(resource)
            return true
        }
    }).into(this)
