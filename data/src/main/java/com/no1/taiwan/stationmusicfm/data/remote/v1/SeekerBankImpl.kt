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

package com.no1.taiwan.stationmusicfm.data.remote.v1

import com.google.gson.Gson
import com.no1.taiwan.stationmusicfm.data.data.musicbank.MusicInfoData
import com.no1.taiwan.stationmusicfm.data.remote.config.MusicSeekerConfig
import com.no1.taiwan.stationmusicfm.data.remote.services.SeekerBankService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.HttpUrl
import org.jsoup.Jsoup

class SeekerBankImpl : SeekerBankService {
    override fun retrieveSearchMusic(queries: Map<String, String>): Deferred<MusicInfoData> {
        val domainUrl = "${MusicSeekerConfig().apiBaseUrl}${MusicSeekerConfig.API_REQUEST}"
        val httpUrl = HttpUrl.parse(domainUrl)?.newBuilder()?.apply {
            queries.forEach { (k, v) -> addQueryParameter(k, v) }
        }?.build()

        val doc = Jsoup.connect(httpUrl.toString()).get()

        return GlobalScope.async { Gson().fromJson<MusicInfoData>(doc.text(), MusicInfoData::class.java) }
    }
}
