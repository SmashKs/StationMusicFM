package com.no1.taiwan.stationmusicfm.internal.di

import com.no1.taiwan.stationmusicfm.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.components.recyclerview.utils.MusicDiffUtil
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_KEYWORD
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.setBinding

/**
 * To provide the necessary objects into the recycler view.
 */
object RecyclerViewModule {
    fun recyclerViewProvider() = Module("Recycler View") {
        import(adapterProvider())
        import(diffUtilProvider())
        import(mappingProvider())
        import(viewHolderProvider())
    }

    private fun mappingProvider() = Module("View Holder Mapping") {
        /** ViewModel Set for [MultiTypeFactory] */
        bind() from setBinding<ViewHolderEntry>()
    }

    private fun adapterProvider() = Module("Recycler View Adapter") {
        bind<MultiTypeFactory>() with instance(MultiTypeFactory())
    }

    private fun diffUtilProvider() = Module("Recycler View DiffUtil") {
        bind<MusicDiffUtil>(UTIL_DIFF_KEYWORD) with instance(MusicDiffUtil())
    }

    private fun viewHolderProvider() = Module("Viewholder Module") {
        // *** ViewHolder
    }
}
