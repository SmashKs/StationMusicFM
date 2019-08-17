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

import com.no1.taiwan.stationmusicfm.domain.models.Model
import com.no1.taiwan.stationmusicfm.domain.usecases.BaseUsecase
import com.no1.taiwan.stationmusicfm.entities.Entity
import com.no1.taiwan.stationmusicfm.entities.mappers.Mapper

/**
 * Connected [com.no1.taiwan.newsbasket.domain.DeferredUsecase] and execute the usecase work without
 * the mapper (Because the variables should be primitive variable).
 *
 * @param parameter the usecase's parameter.
 */
suspend fun <M : Any, V : BaseUsecase.RequestValues> AsyncCase<M, V>.exec(
    parameter: V? = null
) = execute(parameter)

/**
 * Connected [com.no1.taiwan.newsbasket.domain.DeferredUsecase] and execute the usecase work with
 * the mapper for transforming to an object for presentation layer. (Because the variables should be
 * primitive variable).
 *
 * @param mapper the mapper for transforming from [Model] to [Entity].
 * @param parameter the usecase's parameter.
 */
suspend fun <M : Model, E : Entity, V : BaseUsecase.RequestValues> AsyncCase<M, V>.execMapping(
    mapper: Mapper<M, E>,
    parameter: V? = null
) = execute(parameter).run(mapper::toEntityFrom)

/**
 * Connected [com.no1.taiwan.newsbasket.domain.DeferredUsecase] and execute the usecase work with
 * the mapper for transforming to a list of object for presentation layer. (Because the variables should be
 * primitive variable).
 *
 * @param mapper the mapper for transforming from List<[Model]> to List<[Entity]>.
 * @param parameter the usecase's parameter.
 */
suspend fun <M : Model, E : Entity, V : BaseUsecase.RequestValues, MS : List<M>> AsyncCase<MS, V>.execListMapping(
    mapper: Mapper<M, E>,
    parameter: V? = null
) = execute(parameter).map(mapper::toEntityFrom)
