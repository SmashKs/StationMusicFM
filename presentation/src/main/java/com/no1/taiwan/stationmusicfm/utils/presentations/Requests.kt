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

package com.no1.taiwan.stationmusicfm.utils.presentations

import com.no1.taiwan.stationmusicfm.data.BuildConfig
import com.no1.taiwan.stationmusicfm.domain.ResponseState
import kotlinx.coroutines.runBlocking

/**
 * A transformer wrapper for encapsulating the [RespMutableLiveData]'s state
 * changing, and the state becomes [Success] when retrieving a data from RankInfoData layer by Kotlin coroutine.
 *
 * Also, unboxing the [ResponseState] and obtaining the data inside of the [ResponseState], then return the
 * data to [RespMutableLiveData].
 */
fun <E, R> RespMutableLiveData<R>.reqDataMap(usecaseRes: E, transformBlock: (E) -> R) =
    preProc {
        // Fetching the data from the data layer.
        tryResp {
            val data =
                usecaseRes ?: return@tryResp ResponseState.Error<R>(msg = "Don't have any response.")
            ResponseState.Success(transformBlock(data))
        }.let(this@reqDataMap::postValue)
    }

/**
 * A transformer wrapper for encapsulating the [RespMutableLiveData]'s state
 * changing, and the state becomes [Success] when retrieving a data from RankInfoData layer by Kotlin coroutine.
 */
infix fun <E> RespMutableLiveData<E>.reqData(usecaseRes: suspend () -> E) = preProc {
    // Fetching the data from the data layer.
    tryResp { ResponseState.Success(usecaseRes()) }.let(this@reqData::postValue)
}

/**
 * A transformer wrapper for the [RespMutableLiveData]. This function is more flexibility than
 * [reqData].
 */
infix fun <E> RespMutableLiveData<E>.reqDataWrap(usecaseRes: suspend () -> ResponseState<E>) =
    preProc {
        // Fetching the data from the data layer.
        tryResp { usecaseRes() }.let(this@reqDataWrap::postValue)
    }

/**
 * Pre-process does that showing the loading view.
 */
private fun <E> RespMutableLiveData<E>.preProc(block: suspend () -> Unit) = runBlocking {
    // Opening the loading view.
    postValue(ResponseState.Loading())
    // Fetching the data from the data layer.
    block()
}

/**
 * A transformer wrapper for encapsulating the [RespMutableLiveData]'s state
 * changing, and the state becomes [Success] when retrieving a data from RankInfoData layer by Kotlin coroutine.
 *
 * Also, unboxing the [ResponseState] and obtaining the data inside of the [ResponseState], then return the
 * data to [RespMutableLiveData].
 */
fun <E, R> NotifMutableLiveData<R>.reqDataMap(usecaseRes: E, transformBlock: (E) -> R) =
    preProc {
        // Fetching the data from the data layer.
        tryResp {
            val data =
                usecaseRes ?: return@tryResp ResponseState.Error<R>(msg = "Don't have any response.")
            ResponseState.Success(transformBlock(data))
        }.let(this@reqDataMap::postValue)
    }

/**
 * A transformer wrapper for encapsulating the [RespMutableLiveData]'s state
 * changing, and the state becomes [Success] when retrieving a data from RankInfoData layer by Kotlin coroutine.
 */
infix fun <E> NotifMutableLiveData<E>.reqData(usecaseRes: suspend () -> E) = preProc {
    // Fetching the data from the data layer.
    tryResp { ResponseState.Success(usecaseRes()) }.let(this@reqData::postValue)
}

/**
 * A transformer wrapper for the [RespMutableLiveData]. This function is more flexibility than
 * [reqData].
 */
infix fun <E> NotifMutableLiveData<E>.reqDataWrap(usecaseRes: suspend () -> ResponseState<E>) =
    preProc {
        // Fetching the data from the data layer.
        tryResp { usecaseRes() }.let(this@reqDataWrap::postValue)
    }

/**
 * Pre-process does that showing the loading view.
 */
private fun <E> NotifMutableLiveData<E>.preProc(block: suspend () -> Unit) = runBlocking {
    // Opening the loading view.
    postValue(ResponseState.Loading())
    // Fetching the data from the data layer.
    block()
}

/**
 * Wrapping the `try catch` and ignoring the return value.
 */
private inline fun <E> tryResp(block: () -> ResponseState<E>) = try {
    block()
}
catch (e: Exception) {
    if (BuildConfig.DEBUG)
        e.printStackTrace()
    ResponseState.Error<E>(msg = e.message ?: "Unknown error has happened.")
}
