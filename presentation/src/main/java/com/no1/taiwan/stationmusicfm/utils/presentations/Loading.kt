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

import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinshaver.castOrNull
import com.no1.taiwan.stationmusicfm.bases.LoadView
import com.no1.taiwan.stationmusicfm.domain.ResponseState

/**
 * Check the [ResponseState]'s changing and do the corresponding reaction. Here're three data
 * type [Loading], [Success], and [Error].
 *
 * - [Loading] state will show the loading view.
 * - [Success] state will extract the data from the inside class to be used [successBlock].
 * - [Error] state will show the error view and msg to the user. Also, you can use [errorBlock] manually.
 */
internal class LoadingBuilder<D>(response: ResponseState<D>) {
    var isShowLoading = true
    var isShowError = true
    var isHideLoading = true
    var successBlock by WeakRef<(D) -> Unit>()
    var completedBlock by WeakRef<() -> Unit>()
    var errorBlock by WeakRef<(String) -> Unit>()
    private val response by WeakRef(response)

    fun peel(loadView: LoadView) {
        when (response) {
            is ResponseState.Loading<*> -> if (isShowLoading) loadView.showLoading()
            is ResponseState.Success<D> -> {
                response?.data?.run { successBlock?.invoke(this) }
                if (isShowLoading && isHideLoading) loadView.hideLoading()
                if (isShowLoading) loadView.hideLoading()
                completedBlock?.invoke()
            }
            is ResponseState.Error<*> -> {
                val res = castOrNull<ResponseState.Error<*>>(response)

                if (isShowLoading) loadView.hideLoading()
                if (isShowError) loadView.showError(res?.msg.orEmpty())
                errorBlock?.invoke(res?.msg.orEmpty())
            }
        }
    }
}

/**
 * Check the [ResponseState]'s changing and do the corresponding reaction.
 */
internal inline infix fun <D> ResponseState<D>.peel(noinline λ: (D) -> Unit) =
    LoadingBuilder(this).apply { successBlock = λ }

/**
 * Check the [ResponseState]'s changing and do the corresponding reaction. For the situation which we need
 * to use multi-requests in the same time.
 */
internal inline infix fun <D> ResponseState<D>.peelAndContinue(noinline successBlock: (D) -> Unit) =
    peel(successBlock).apply { isHideLoading = false }

/**
 * Check the [ResponseState]'s changing and do the corresponding reaction without showing the loading view.
 */
internal inline infix fun <D> ResponseState<D>.peelSkipLoading(noinline successBlock: (D) -> Unit) =
    peel(successBlock).apply { isShowLoading = false }

/**
 * Check the [ResponseState]'s changing and do the corresponding reaction with error block and completed block.
 */
internal inline infix fun <D> ResponseState<D>.peelCompleted(noinline completedBlock: () -> Unit) =
    LoadingBuilder(this).apply { this.completedBlock = completedBlock }

internal inline infix fun <D> LoadingBuilder<D>.happenError(noinline errorBlock: (String) -> Unit) = apply {
    this.errorBlock = errorBlock
}

internal inline infix fun <D> LoadingBuilder<D>.finally(noinline completedBlock: () -> Unit) = apply {
    this.completedBlock = completedBlock
}

/**
 * Execute the checking [ResponseState]'s process with the triggering [LoadView].
 */
internal inline infix fun <D> LoadingBuilder<D>.doWith(loadView: LoadView) = peel(loadView)

internal inline infix fun <D> LoadingBuilder<D>.muteErrorDoWith(loadView: LoadView) =
    apply { isShowError = false }.peel(loadView)
