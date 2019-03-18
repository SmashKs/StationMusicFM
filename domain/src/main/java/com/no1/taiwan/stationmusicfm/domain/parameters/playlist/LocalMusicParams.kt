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

package com.no1.taiwan.stationmusicfm.domain.parameters.playlist

import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.ext.UnsupportedOperation

data class LocalMusicParams(
    private val trackName: String = DEFAULT_STR,
    private val artistName: String = DEFAULT_STR,
    private val duration: Int = 0,
    private val hasOwn: Boolean = false,
    private val remoteTrackUri: String = DEFAULT_STR,
    private val localTrackUri: String = DEFAULT_STR,
    private val coverUri: String = DEFAULT_STR,
    private val playlistList: String = DEFAULT_STR,
    private val id: Int = DEFAULT_INT
) : Parameterable {
    companion object {
        const val PARAM_NAME_TRACK_NAME = "local_track_name"
        const val PARAM_NAME_ARTIST_NAME = "local_artist_name"
        const val PARAM_NAME_DURATION = "local_duration"
        const val PARAM_NAME_HAS_OWN = "local_has_own"
        const val PARAM_NAME_REMOTE_TRACK_URI = "local_remote_track_uri"
        const val PARAM_NAME_LOCAL_TRACK_URI = "local_local_track_uri"
        const val PARAM_NAME_COVER_URI = "local_cover_uri"
        const val PARAM_NAME_PLAYLIST_LIST = "local_playlist_list"
        const val PARAM_NAME_PLAYLIST_ID = "local_playlist_id"
    }

    override fun toApiParam() = UnsupportedOperation()

    override fun toApiAnyParam(): HashMap<String, Any> = hashMapOf(
        PARAM_NAME_TRACK_NAME to trackName,
        PARAM_NAME_ARTIST_NAME to artistName,
        PARAM_NAME_DURATION to duration,
        PARAM_NAME_HAS_OWN to hasOwn,
        PARAM_NAME_REMOTE_TRACK_URI to remoteTrackUri,
        PARAM_NAME_LOCAL_TRACK_URI to localTrackUri,
        PARAM_NAME_COVER_URI to coverUri,
        PARAM_NAME_PLAYLIST_LIST to playlistList,
        PARAM_NAME_PLAYLIST_ID to id)
}
