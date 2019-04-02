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

package com.no1.taiwan.stationmusicfm.player.helpers

import android.os.Environment
import android.os.Environment.DIRECTORY_MUSIC
import com.google.android.exoplayer2.util.Log
import com.no1.taiwan.stationmusicfm.player.PlayerEventListener
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadHandler(
    private val url: String,
    filePath: String? = null,
    eventListener: PlayerEventListener? = null
) : Thread() {
    private val tag = "DownloadHandler"
    private val bufferSize = 2048
    private val tempTrackName = "temp_track.mp3"
    private val tempTrackPath =
        Environment.getExternalStorageDirectory().toString() + "/" + DIRECTORY_MUSIC + "/" + tempTrackName
    private var totalSize: Int = -1
    private var path: String? = null
    private var file: File? = null
    private var listener: PlayerEventListener? = null

    init {
        path = when (filePath.isNullOrEmpty()) {
            true -> tempTrackPath
            false -> filePath
        }
        listener = eventListener
    }

    override fun run() {
        super.run()
        Runnable {
            if (!isRemoteAvailable()) {
                listener?.onDownloadTrack(false)
                Thread.currentThread().interrupt()
            }

            if (!createFile()) {
                listener?.onDownloadTrack(false)
                Thread.currentThread().interrupt()
            }

            if (!downloadFile()) {
                listener?.onDownloadTrack(false)
                Thread.currentThread().interrupt()
            }
            else {
                listener?.onDownloadTrack(true)
            }
        }.run()
    }

    private fun isRemoteAvailable(): Boolean {
        var result = false
        Log.i(tag, "url is $url")
        val httpURLConnection = URL(url).openConnection() as HttpURLConnection
        HttpURLConnection.setFollowRedirects(false)
        httpURLConnection.connect()
        httpURLConnection.requestMethod = "GET"
        if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK)
            result = true

        totalSize = httpURLConnection.contentLength
        httpURLConnection.disconnect()
        return result
    }

    private fun createFile(): Boolean {
        file = File(path)
        file?.takeIf { it.exists() }.let {
            it?.createNewFile()
        }

        return true
    }

    private fun downloadFile(): Boolean {
        val fileOutputStream = FileOutputStream(file)
        val inputStream = BufferedInputStream(URL(url).openStream())
        var downloadSize = 0
        val buffer = ByteArray(bufferSize)
        var bufferLength: Int
        fileOutputStream.use { fos ->
            inputStream.use { bis ->
                while (true) {
                    bufferLength = bis.read(buffer)
                    if (bufferLength <= 0)
                        break
                    fos.write(buffer, 0, bufferLength)
                    downloadSize += bufferLength
                }
            }
            fos.flush()
        }

        if (downloadSize != totalSize) {
            Log.e(tag, "file was not complete")
            return false
        }
        return true
    }
}
