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

package com.no1.taiwan.stationmusicfm.domain

import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob

/**
 * A base abstract class for wrapping a coroutine [Deferred] object and do the error handling
 * when an error or cancellation happened.
 */
abstract class DeferredUsecase<T : Any, R : BaseUsecase.RequestValues> : BaseUsecase<R> {
    /** The main job for the top schedule. */
    private val parentJob get() = SupervisorJob()

    internal abstract suspend fun acquireCase(): T

    open suspend fun execute(parameter: R? = null) = let {
        parameter?.let { requestValues = it }

        // If the parent job was cancelled that will happened an exception, that's
        // why we should create a new job instead.
        acquireCase()
    }

    protected suspend fun attachParameter(λ: suspend (R) -> T) = requireNotNull(requestValues?.run { λ(this) })
}
