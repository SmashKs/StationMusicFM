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

import android.os.Bundle
import com.devrapid.kotlinknifer.logw
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.bases.AdvActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class TestActivity : AdvActivity<TestViewModel>() {
    override fun provideLayoutId() = R.layout.activity_test

    /**
     * Initialize doing some methods and actions.
     *
     * @param savedInstanceState previous state after this activity was destroyed.
     */
    override fun init(savedInstanceState: Bundle?) {
        GlobalScope.launch {
            val name = "Iggy+Azalea"
            val page = 1
            val doc = Jsoup.connect("https://www.last.fm/music/$name/+images?page=$page").get()
            doc.select("ul.image-list").select("li").map {
                it.select("img.image-list-image").attr("src")
            }.map {
                it.split("/").last()
            }.map {
                "https://lastfm-img2.akamaized.net/i/u/770x0/$it.jpg"
            }.forEach {
                logw(it)
            }
            val hasNext = doc.select("li.pagination-next").select("a").toString().isNotBlank()
            logw(hasNext)
        }
    }
}
