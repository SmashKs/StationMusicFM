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

package com.no1.taiwan.stationmusicfm.player

class ExoPlayerEventListener {
    class PlayerEventListenerFunc {
        var onDurationChanged: ((duration: Int) -> Unit)? = null
        var onBufferPercentage: ((percent: Int) -> Unit)? = null
        var onCurrentTime: ((second: Int) -> Unit)? = null
        var onPlayerStateChanged: ((state: MusicPlayerState) -> Unit)? = null
        var onDownloadTrack: ((isSuccess: Boolean) -> Int)? = null
    }

    class PlayerEventListener(func: PlayerEventListenerFunc.() -> Unit) : EventListener {
        private var func = PlayerEventListenerFunc().apply(func)

        override fun onDurationChanged(duration: Int) {
            func.onDurationChanged?.invoke(duration)
        }

        override fun onBufferPercentage(percent: Int) {
            func.onBufferPercentage?.invoke(percent)
        }

        override fun onCurrentTime(second: Int) {
            func.onCurrentTime?.invoke(second)
        }

        override fun onPlayerStateChanged(state: MusicPlayerState) {
            func.onPlayerStateChanged?.invoke(state)
        }

        override fun onDownloadTrack(isSuccess: Boolean) {
            func.onDownloadTrack?.invoke(isSuccess)
        }
    }
}
