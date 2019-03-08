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

package com.no1.taiwan.stationmusicfm.utils.aac

import com.no1.taiwan.stationmusicfm.domain.ResponseState
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.NotifMutableLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespLiveData
import com.no1.taiwan.stationmusicfm.utils.presentations.RespMutableLiveData

fun <D> RespMutableLiveData<D>.data() = value?.data
fun <D> RespLiveData<D>.data() = value?.data
fun <D> NotifMutableLiveData<D>.data() = value?.data
fun <D> NotifLiveData<D>.data() = value?.data

fun <D> RespMutableLiveData<D>.errMsg() = (value as? ResponseState.Error<*>)?.msg
fun <D> RespLiveData<D>.errMsg() = (value as? ResponseState.Error<*>)?.msg
fun <D> NotifMutableLiveData<D>.errMsg() = (value as? ResponseState.Error<*>)?.msg
fun <D> NotifLiveData<D>.errMsg() = (value as? ResponseState.Error<*>)?.msg
