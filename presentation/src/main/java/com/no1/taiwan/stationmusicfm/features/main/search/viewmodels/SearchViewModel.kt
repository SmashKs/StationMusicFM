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
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_STR
import com.no1.taiwan.stationmusicfm.features.SearchHistories
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel
import com.no1.taiwan.stationmusicfm.utils.aac.delegates.PreziMapperDigger
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.utils.presentations.execListMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.execMapping
import com.no1.taiwan.stationmusicfm.utils.presentations.reqData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchMusicCase: FetchMusicCase,
    private val addSearchHistoryCase: AddSearchHistCase,
    private val deleteSearchHistoriesCase: DeleteSearchHistCase,
    private val fetchSearchHistoriesCase: FetchSearchHistsCase,
    diggerDelegate: PreziMapperDigger
) : AutoViewModel(), PreziMapperDigger by diggerDelegate {
    private val _musics by lazy { RespMutableLiveData<MusicEntity>() }
    val musics: RespLiveData<MusicEntity> = _musics
    private val _histories by lazy { RespMutableLiveData<SearchHistories>() }
    val histories: RespLiveData<SearchHistories> = _histories
    private val _removeRes by lazy { RespMutableLiveData<Boolean>() }
    val removeRes: RespLiveData<Boolean> = _removeRes
    private val musicMapper by lazy { digMapper(MusicPMapper::class) }
    private val historyMapper by lazy { digMapper(SearchHistoryPMapper::class) }
    val keyword = MutableLiveData<String>(DEFAULT_STR)

    fun runTaskSearchMusic(keyword: String) = GlobalScope.launch {
        this@SearchViewModel.keyword.postValue(keyword)
        _musics reqData { fetchMusicCase.execMapping(musicMapper, FetchMusicReq(SrchSongParams(keyword))) }
    }

    fun runTaskAddHistory(keyword: String) = GlobalScope.launch {
        this@SearchViewModel.keyword.postValue(keyword)
        addSearchHistoryCase.exec(AddSearchHistReq(SearchHistParams(keyword)))
    }

    fun runTaskFetchHistories() = GlobalScope.launch {
        _histories reqData {
            fetchSearchHistoriesCase.execListMapping(historyMapper, FetchSearchHistsReq(SearchHistParams(limit = 100)))
        }
    }

    fun runTaskDeleteHistory(keyword: String) = GlobalScope.launch {
        _removeRes reqData { deleteSearchHistoriesCase.exec(DeleteSearchHistReq(SearchHistParams(keyword))) }
    }
}
