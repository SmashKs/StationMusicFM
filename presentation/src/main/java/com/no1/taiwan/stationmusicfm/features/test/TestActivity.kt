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

package com.no1.taiwan.stationmusicfm.features.test

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import com.devrapid.kotlinknifer.toBitmap
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvActivity
import com.no1.taiwan.stationmusicfm.utils.bitmap.decorateGradientMask
import org.jetbrains.anko.find

class TestActivity : AdvActivity<TestViewModel>() {
    override fun provideLayoutId() = R.layout.activity_test

    /**
     * Initialize doing some methods and actions.
     *
     * @param savedInstanceState previous state after this activity was destroyed.
     */
    override fun init(savedInstanceState: Bundle?) {
        val shaderA = LinearGradient(0f,
                                     0f,
                                     0f,
                                     R.drawable.lady_gaga.toBitmap(this).height.toFloat() - 100,
                                     Color.BLACK,
                                     Color.TRANSPARENT,
                                     Shader.TileMode.CLAMP)
        val res = R.drawable.lady_gaga.toBitmap(this).decorateGradientMask(shaderA)

        find<ImageView>(R.id.iv_pic).setImageBitmap(res)
    }
}
