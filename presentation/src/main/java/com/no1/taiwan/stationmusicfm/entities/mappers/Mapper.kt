package com.no1.taiwan.stationmusicfm.entities.mappers

import com.no1.taiwan.stationmusicfm.domain.models.Model
import com.no1.taiwan.stationmusicfm.entities.Entity

/**
 * The mapper is used to transition the object between [Model] and [Entity].
 */
interface Mapper<M : Model, E : Entity> {
    /**
     * Transition the [Model] object to [Entity] object.
     *
     * @param model a [Model] model object.
     * @return the same content's [Entity] object.
     */
    fun toEntityFrom(model: M): E

    /**
     * Transition the [Model] object to [Entity] object.
     *
     * @param entity a [Entity] data object.
     * @return the same content's [Model] object.
     */
    fun toModelFrom(entity: E): M
}
