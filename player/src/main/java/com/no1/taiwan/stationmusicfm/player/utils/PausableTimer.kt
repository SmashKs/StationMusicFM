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

package com.no1.taiwan.stationmusicfm.player.utils

import android.os.CountDownTimer

class PausableTimer(
    private val millisInFuture: Long = -1,
    private val countDownInterval: Long = 1000
) {
    var onTick: (millisUntilFinished: Long) -> Unit = {}
    var onFinish: () -> Unit = {}
    var isPause = false
    var isStart = false
    var curTime = 0L
    private lateinit var timer: CountDownTimer

    init {
        val millisTime = if (-1L == millisInFuture) Long.MAX_VALUE else millisInFuture
        init(millisTime, countDownInterval)
    }

    fun pause(): Long {
        isPause = true
        stop()

        return curTime
    }

    fun resume() {
        val time = if (isPause && 0 <= curTime) {
            isPause = false
            curTime
        }
        else {
            millisInFuture
        }

        stop()
        init(time, countDownInterval)
        start()
    }

    fun start() {
        if (!isStart && !isPause) {
            if (0L == curTime) {
                init(millisInFuture, countDownInterval)
            }
            timer.start()
            isStart = true
        }
        else if (isPause) {
            resume()
        }
    }

    fun stop() {
        timer.cancel()
        isStart = false
    }

    private fun init(millisInFuture: Long, countDownInterval: Long) {
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                // OPTIMIZE(jieyi): 9/29/17
                // val time = (Math.round(millisUntilFinished.toDouble() / 1000) - 1).toInt()
                this@PausableTimer.curTime = millisUntilFinished
                this@PausableTimer.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                this@PausableTimer.curTime = 0
                this@PausableTimer.onFinish()
            }
        }
    }

}
