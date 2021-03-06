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

package com.no1.taiwan.stationmusicfm.entities.playlist

import com.no1.taiwan.stationmusicfm.entities.Entity
import com.no1.taiwan.stationmusicfm.entities.MusicEncoder
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.utils.file.MusicEncode
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiVisitable
import java.util.Date

data class LocalMusicEntity(
    val id: Int = 0,
    val trackName: String = DEFAULT_STR,
    val artistName: String = DEFAULT_STR,
    val duration: Int = 0,
    val hasOwn: Boolean = false,
    val remoteTrackUri: String = DEFAULT_STR,
    val localTrackUri: String = DEFAULT_STR,
    val playlistList: String = DEFAULT_STR,
    val coverUri: String = DEFAULT_STR,
    val created: Date = Date(),
    val lastListen: Date = Date()
) : Entity, MusicEncoder, MusicMultiVisitable {
    override fun type(typeFactory: MultiTypeFactory) = typeFactory.type(this)

    override fun encodeByName() = MusicEncode.encodeMusicBy(artistName, trackName)

    override fun getMusicUri() = remoteTrackUri
}
