package com.no1.taiwan.stationmusicfm.components.recyclerview

import com.devrapid.adaptiverecyclerview.ViewTypeFactory
import com.no1.taiwan.stationmusicfm.internal.di.RecyclerViewModule.recyclerViewProvider
import com.no1.taiwan.stationmusicfm.internal.di.ViewHolderEntries
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * A factory for providing the viewholder from an object data type to the recyclerview.
 */
class MultiTypeFactory : ViewTypeFactory(), KodeinAware {
    override var transformMap
        get() = viewHolderEntries.toMap().toMutableMap()
        set(_) = throw UnsupportedOperationException("We don't allow this method to use!")
    override val kodein = Kodein.lazy {
        import(recyclerViewProvider())
    }
    private val viewHolderEntries by instance<ViewHolderEntries>()

    // *** Here are the entity binding the specific hashcode. ***
    fun type(entity: MusicMultiVisitable) = entity.javaClass.hashCode()
}
