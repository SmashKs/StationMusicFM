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

package com.no1.taiwan.stationmusicfm.utils

object RxBusConstant {
    object Tag {
        const val TAG_RANK_DETAIL = "rank detail"
        const val TAG_TO_DETAIL = "goto detail fragment"
        const val TAG_TO_ALBUM = "goto album detail fragment"
        const val TAG_TO_SIMILAR_ARTIST = "goto similar artist fragment"
        const val TAG_REMOVING_SEARCH_HIST = "removing the searching history"
        const val TAG_SAVING_SEARCH_HIST = "saving the searching history"
        const val TAG_PLAY_A_SONG = "play a song for asking the exo player"
    }

    object Parameter {
        const val PARAMS_COMMON_TITLE = "title"
        const val PARAMS_COMMON_MBID = "mbid"
        const val PARAMS_COMMON_ARTIST_NAME = "artist name"

        const val PARAMS_TO_DETAIL_TARGET = "target"

        const val PARAMS_TO_GENRE_NAME = "genre name"

        const val PARAMS_TO_ALBUM_NAME = "album name"
        const val PARAMS_TO_ALBUM_URI = "album uri"

        const val PARAMS_TO_TRACK_NAME = "track name"

        const val PARAMS_TO_RANK_ID = "rank id"

        const val PARAMS_TRACK_URI = "track uri"
        const val PARAMS_LAYOUT_POSITION = "viewholder position"
    }
}
