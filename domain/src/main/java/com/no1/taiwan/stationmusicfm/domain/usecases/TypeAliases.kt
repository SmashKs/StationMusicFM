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

package com.no1.taiwan.stationmusicfm.domain.usecases

import com.no1.taiwan.stationmusicfm.domain.DeferredUsecase
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.CommonMusicModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.HotPlaylistModel
import com.no1.taiwan.stationmusicfm.domain.models.musicbank.MusicInfoModel
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchHotListRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchSongListRespCase

typealias FetchRankMusicReq = FetchRankMusicRespCase.Request
typealias FetchSongListReq = FetchSongListRespCase.Request
typealias FetchMusicReq = FetchMusicRespCase.Request
typealias FetchHotListReq = FetchHotListRespCase.Request

typealias FetchRankMusicCase = DeferredUsecase<MusicInfoModel.MusicModel, FetchRankMusicReq>
typealias FetchSongListCase = DeferredUsecase<CommonMusicModel.PlayListModel, FetchSongListReq>
typealias FetchMusicCase = DeferredUsecase<MusicInfoModel.MusicModel, FetchMusicReq>
typealias FetchHotListCase = DeferredUsecase<HotPlaylistModel.HotListModel, FetchHotListReq>
