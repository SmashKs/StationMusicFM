package com.no1.taiwan.stationmusicfm.internal.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.work.WorkManager
import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

/**
 * To provide the necessary utility objects for the whole app.
 */
object UtilModule {
    fun utilProvider(context: Context) = Module("Util Module") {
        bind<WorkManager>() with instance(WorkManager.getInstance())
        // OPTIMIZE(jieyi): 2018/10/16 We might use Gson for mapping data.
        bind<Gson>() with singleton {
            with(GsonBuilder()) {
                setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                setLenient()
                create()
            }
        }

        // Linear Layout Manager.
        bind<LinearLayoutManager>(ObjectLabel.LINEAR_LAYOUT_VERTICAL) with provider {
            LinearLayoutManager(context, VERTICAL, false)
        }
        bind<LinearLayoutManager>(ObjectLabel.LINEAR_LAYOUT_HORIZONTAL) with provider {
            LinearLayoutManager(context, HORIZONTAL, false)
        }
    }

    /**
     * Import this module in the [com.no1.taiwan.newsbasket.App] because data layer needs this.
     */
    fun dataUtilProvider() = Module("RankInfoData Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper] */
        bind() from setBinding<DataMapperEntry>()

        /** RankInfoData Layer Mapper */
    }

    /**
     * Import this module for each activity entry, they don't be needed in the beginning.
     */
    fun presentationUtilProvider(context: Context) = Module("Presentation Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.data.data.mappers.Mapper] */
        bind() from setBinding<PresentationMapperEntry>()

        /** Presentation Layer Mapper */
    }
}
