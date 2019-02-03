package com.no1.taiwan.stationmusicfm.components.recyclerview

import android.view.ViewGroup
import com.devrapid.adaptiverecyclerview.AdaptiveDiffUtil
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.internal.di.RecyclerViewModule
import com.no1.taiwan.stationmusicfm.widget.extensions.components.recyclerview.helpers.AdapterItemTouchHelper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * The common recyclerview adapter for the multi-type object. Avoid that we create
 * a lots similar boilerplate adapters.
 */
open class MultiTypeAdapter(
    override var dataList: MultiData,
    private val externalDiffUtil: AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>? = null
) : MusicAdapter(), KodeinAware, AdapterItemTouchHelper {
    override var typeFactory: MultiTypeFactory
        get() = multiTypeFactory
        set(_) = throw UnsupportedOperationException("We don't allow this method to use!")
    override var diffUtil: AdaptiveDiffUtil<MultiTypeFactory, MusicMultiVisitable>
        get() = externalDiffUtil ?: super.diffUtil
        set(_) = throw UnsupportedOperationException("We don't allow this method to use!")
    override val kodein = Kodein.lazy {
        import(RecyclerViewModule.recyclerViewProvider())
    }
    protected var viewType = DEFAULT_INT
    private val multiTypeFactory by instance<MultiTypeFactory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        this.viewType = viewType

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onItemSwiped(position: Int) {
        dropAt(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
    }
}
