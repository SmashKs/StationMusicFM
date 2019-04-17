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

import com.no1.taiwan.stationmusicfm.data.data.lastfm.ArtistPhotosData
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmExtraService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup

class LastFmExtraImpl : LastFmExtraService {
    companion object {
        private const val LASTFM_DOMAIN = "https://www.last.fm/music/"
        private const val LASTFM_IMAGE_REQUEST = "/+images?"

        private const val LASTFM_IMAGE_URL = "https://lastfm-img2.akamaized.net/i/u/770x0/%s.jpg"
    }

    override fun retrieveArtistPhotosInfo(artistName: String, page: Int) = CoroutineScope(Dispatchers.IO).async {
        val doc = Jsoup.connect("$LASTFM_DOMAIN$artistName$LASTFM_IMAGE_REQUEST?page=$page").get()

        val hasNext = doc.select("li.pagination-next").select("a").toString().isNotBlank()
        val photos = doc.select("ul.image-list").select("li")
            .map { it.select("img.image-list-image").attr("src") }
            .map { it.split("/").last() }
            .map { ArtistPhotosData.ArtistPhotoData(LASTFM_IMAGE_URL.format(it), it) }

        ArtistPhotosData(photos, hasNext)
    }
}
