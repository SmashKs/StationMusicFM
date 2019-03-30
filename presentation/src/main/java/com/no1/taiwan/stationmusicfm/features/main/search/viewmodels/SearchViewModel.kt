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

package com.no1.taiwan.stationmusicfm.features.main.search.viewmodels

import androidx.lifecycle.MutableLiveData
import com.no1.taiwan.stationmusicfm.domain.parameters.history.SearchHistParams
import com.no1.taiwan.stationmusicfm.domain.parameters.musicbank.SrchSongParams
import com.no1.taiwan.stationmusicfm.domain.usecases.AddSearchHistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddSearchHistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.DeleteSearchHistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.DeleteSearchHistReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicReq
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSearchHistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSearchHistsReq
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.others.SearchHistoryPMapper
import com.no1.taiwan.stationmusicfm.entities.musicbank.MusicInfoEntity.MusicEntity
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.SearchHistories
import com.no1.taiwan.stationmusicfm.utils.aac.data
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.LocalMusicDelegate
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.MakeLocalMusic
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.aac.viewmodels.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.utils.presentations.execListMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData

class SearchViewModel(
    private val fetchMusicCase: FetchMusicCase,
    private val addSearchHistoryCase: AddSearchHistCase,
    private val deleteSearchHistoriesCase: DeleteSearchHistCase,
    private val fetchSearchHistoriesCase: FetchSearchHistsCase,
    makeLocalMusic: MakeLocalMusic,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate, LocalMusicDelegate by makeLocalMusic {
    private val _musics by lazy { RespMutableLiveData<MusicEntity>() }
    val musics: RespLiveData<MusicEntity> = _musics
    private val _histories by lazy { RespMutableLiveData<SearchHistories>() }
    val histories: RespLiveData<SearchHistories> = _histories
    private val _removeRes by lazy { RespMutableLiveData<Boolean>() }
    val removeRes: RespLiveData<Boolean> = _removeRes
    private val musicMapper by lazy { digMapper(MusicPMapper::class) }
    private val historyMapper by lazy { digMapper(SearchHistoryPMapper::class) }
    private val page by lazy { MutableLiveData(0) }
    val keyword by lazy { MutableLiveData(DEFAULT_STR) }

    fun runTaskSearchMusic(keyword: String) = launchBehind {
        this@SearchViewModel.keyword.postValue(keyword)
        if (page.value != DEFAULT_INT) {  // -1 means to the end.
            _musics reqData {
                fetchMusicCase.execMapping(musicMapper,
                                           FetchMusicReq(SrchSongParams(keyword, requireNotNull(page.value))))
            }
        }
    }

    fun runTaskAddHistory(keyword: String) = launchBehind {
        this@SearchViewModel.keyword.postValue(keyword)
        addSearchHistoryCase.exec(AddSearchHistReq(SearchHistParams(keyword)))
    }

    fun runTaskFetchHistories() = launchBehind {
        _histories reqData {
            fetchSearchHistoriesCase.execListMapping(historyMapper, FetchSearchHistsReq(SearchHistParams(limit = 100)))
        }
    }

    fun runTaskDeleteHistory(keyword: String) = launchBehind {
        _removeRes reqData { deleteSearchHistoriesCase.exec(DeleteSearchHistReq(SearchHistParams(keyword))) }
    }

    fun increasePageNumber() {
        page.value = if (_musics.data()?.items?.isEmpty() == false) requireNotNull(page.value) + 1 else -1
    }

    fun getCurPageNumber() = requireNotNull(page.value)

    fun resetPageNumber() = page.postValue(0)
}

