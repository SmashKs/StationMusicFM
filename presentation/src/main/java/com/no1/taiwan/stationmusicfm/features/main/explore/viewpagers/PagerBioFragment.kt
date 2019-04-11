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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewpagers

import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.core.text.toSpannable
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.utils.aac.observeNonNull
import com.no1.taiwan.stationmusicfm.utils.presentations.doWith
import com.no1.taiwan.stationmusicfm.utils.presentations.peel
import org.jetbrains.anko.support.v4.find

class PagerBioFragment : BasePagerFragment() {
    private var bio: CharSequence = DEFAULT_STR
    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    override fun bindLiveData() {
        observeNonNull(vm.artistLiveData) {
            peel {
                // The previous data is showed (Auto emit when artist live data isn't null) before the newest
                // data will be fetched when count is 0.
                // That's why count is 1 then it's allowed passing. If the same artist, fetching the same
                // data again isn't expected, at this condition, count will be 0.
                if ((enterCount == 0 && searchArtistName == it.name) || enterCount == 1) {
                    bio = it.bio.content.parseAsHtml().toSpannable()
                    find<TextView>(R.id.ftv_bio).text = bio
                }
            } doWith this@PagerBioFragment
        }
    }

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    override fun viewComponentBinding() {
        // ** Don't call the super function.
        // After view come again, we should show again. If count is less than 1, instead here, the callback
        // will show.
        if (enterCount >= 1 && bio.isNotBlank()) {
            find<TextView>(R.id.ftv_bio).text = bio
        }
    }

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    override fun provideInflateView() = R.layout.viewpager_bio
}
