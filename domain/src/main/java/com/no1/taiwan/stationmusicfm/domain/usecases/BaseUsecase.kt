package com.no1.taiwan.stationmusicfm.domain.usecases

import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase.RequestValues

/**
 * Interface for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case in the
 * application should implement this contract).
 *
 * By convention each Use Case implementation will return the result using
 * that will execute its job in a background thread and will post the result in the UI thread.
 *
 * For passing a request parameters [RequestValues] to data layer that set a generic type for wrapping
 * vary data.
 *
 * @param R The parameters from from Presentation layer to Data layer.
 * @property requestValues An interface for base class.
 */
interface BaseUsecase<R : BaseUsecase.RequestValues> {
    /** Provide a common parameter variable for the children class. */
    var requestValues: R?

    /** Interface for wrap a data for passing to a request.*/
    interface RequestValues
}
