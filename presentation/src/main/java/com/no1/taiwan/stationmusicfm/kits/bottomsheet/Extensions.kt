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

package com.no1.taiwan.stationmusicfm.kits.bottomsheet

import android.widget.TextView
import com.devrapid.kotlinknifer.DrawableDirectionConst.DRAWABLE_DIRECTION_START
import com.devrapid.kotlinknifer.addDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.no1.taiwan.stationmusicfm.R
import org.jetbrains.anko.findOptional

fun BottomSheetDialog.assignDownloadIcon(isDownload: Boolean) = apply {
    val tvDownload = findOptional<TextView>(R.id.ftv_download) ?: return@apply
    val downloadDrawable = if (isDownload) R.drawable.ic_cloud_download_black else R.drawable.ic_cloud_downloaded_black
    tvDownload.addDrawable(downloadDrawable, DRAWABLE_DIRECTION_START)
}
