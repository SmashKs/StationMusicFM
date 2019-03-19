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

package com.no1.taiwan.stationmusicfm.player.playlist

import java.util.ArrayDeque

class PlaylistQueue : Playlist<String> {
    private val queue by lazy { ArrayDeque<String>(30) }
    private var index = 0
    override var mode: Playlist.Mode = Playlist.Mode.Default
    override var current = if (size > 0) queue.elementAt(index) else ""
    override var previous = ""
    override val next: String
        get() {
            if (mode != Playlist.Mode.RepeatOne)
                previous = queue.elementAt(index)
            index = when (mode) {
                is Playlist.Mode.Default -> (index + 1).takeIf { it < size } ?: -1
                is Playlist.Mode.RepeatOne -> index
                is Playlist.Mode.RepeatAll -> (index + 1) % size
                is Playlist.Mode.Shuffle -> (0..size - 1).random()
            }
            return queue.elementAt(index)
        }
    override val size get() = queue.size

    override fun dequeue() = if (queue.size > 0) queue.poll() else null

    override fun enqueue(objs: List<String>) = queue.addAll(objs)

    override fun enqueue(obj: String) = queue.add(obj)

    override fun clear() = queue.clear()
}
