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

package com.no1.taiwan.stationmusicfm.utils.file

import com.no1.taiwan.stationmusicfm.MusicApp
import java.io.File

object FilePathFactory {
    fun obtainMusicPath(fileName: String): String {
        val musicDir = File(MusicApp.appContext.filesDir, "music").apply {
            if (!exists())
                mkdir()
        }
        return listOf(musicDir.toString(), fileName).joinToString("/", postfix = ".mp3")
    }

    fun getMusicPath(fileName: String) = obtainMusicPath(fileName).takeIf { File(it).exists() }

    fun removeMusic(filePath: String): Boolean {
        val fileDelete = File(filePath)
        if (!fileDelete.exists()) return false
        return fileDelete.delete()
    }
}
