package com.no1.taiwan.stationmusicfm.domain

import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob

/**
 * A base abstract class for wrapping a coroutine [Deferred] object and do the error handling
 * when an error or cancellation happened.
 */
abstract class DeferredUsecase<T : Any, R : BaseUsecase.RequestValues> :
    BaseUsecase<R> {
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
