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

package com.no1.taiwan.stationmusicfm.domain.models.rank

import com.no1.taiwan.stationmusicfm.domain.models.Model
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR

object CommonMusicModel {
    data class UserModel(
        val address: String = DEFAULT_STR,
        val avatarUrl: String = DEFAULT_STR,
        val birthday: Int = 0,
        val email: String = DEFAULT_STR,
        val gender: Int = 0,
        val phone: String = DEFAULT_STR,
        val platform: Int = 0,
        val platformUid: String = DEFAULT_STR,
        val screenName: String = DEFAULT_STR,
        val uid: String = DEFAULT_STR
    ) : Model

    data class SongModel(
        val artist: String = DEFAULT_STR,
        val cdnCoverUrl: String = DEFAULT_STR,
        val copyrightType: Int = 0,
        val coverURL: String = DEFAULT_STR,
        val flag: Int = 0,
        val length: Int = 0,
        val lyricURL: String = DEFAULT_STR,
        val mv: MvModel = MvModel(),
        val oriCoverUrl: String = DEFAULT_STR,
        val otherSources: List<Any> = emptyList(),
        val shareUri: String = DEFAULT_STR,
        val sid: Int = 0,
        val songIdExt: String = DEFAULT_STR,
        val source: SourceModel = SourceModel(),
        val title: String = DEFAULT_STR,
        val uploader: String = DEFAULT_STR,
        val url: String = DEFAULT_STR
    ) : Model

    data class PlayListModel(
        val commentCount: Int = 0,
        val favCount: Int = 0,
        val hasFav: Boolean = false,
        val isCoverModified: Boolean = false,
        val permission: Int = 0,
        val playedCount: String = DEFAULT_STR,
        val shareCount: Int = 0,
        val shareLink: String = DEFAULT_STR,
        val shareUri: String = DEFAULT_STR,
        val songListCover: String = DEFAULT_STR,
        val songListDesc: String = DEFAULT_STR,
        val songListId: String = DEFAULT_STR,
        val songListName: String = DEFAULT_STR,
        val songListType: Int = 0,
        val songNum: Int = 0,
        val songs: List<SongModel> = emptyList(),
        val tagIds: List<Any> = emptyList(),
        val tags: List<Any> = emptyList(),
        val user: UserModel = UserModel()
    ) : Model

    data class MvModel(
        val comments: Int = 0,
        val coverImage: String = DEFAULT_STR,
        val ctime: String = DEFAULT_STR,
        val description: String = DEFAULT_STR,
        val dislikes: Int = 0,
        val duration: String = DEFAULT_STR,
        val embeddable: Int = 0,
        val fmMvActive: Int = 0,
        val id: Int = 0,
        val isActive: Int = 0,
        val isPublic: Int = 0,
        val languageId: Int = 0,
        val likes: Int = 0,
        val mtime: String = DEFAULT_STR,
        val publishedAt: String = DEFAULT_STR,
        val rate: Int = 0,
        val regionAllowed: String = DEFAULT_STR,
        val regionBlocked: String = DEFAULT_STR,
        val reviewInfo: String = DEFAULT_STR,
        val source: Int = 0,
        val title: String = DEFAULT_STR,
        val views: Long = 0,
        val yVideoId: String = DEFAULT_STR
    ) : Model

    data class SourceModel(
        val unknown: Any? = null
    ) : Model
}
