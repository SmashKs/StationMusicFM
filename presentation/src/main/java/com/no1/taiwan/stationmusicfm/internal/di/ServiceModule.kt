package com.no1.taiwan.stationmusicfm.internal.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.no1.taiwan.stationmusicfm.data.local.config.MusicDatabase
import com.no1.taiwan.stationmusicfm.data.remote.RestfulApiFactory
import com.no1.taiwan.stationmusicfm.data.remote.config.LastFmConfig
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService
import com.no1.taiwan.stationmusicfm.internal.di.NetModule.netProvider
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKV.SINGLE_PROCESS_MODE
import com.tencent.mmkv.MMKV.defaultMMKV
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * To provide the necessary objects for the remote/local data store service.
 */
object ServiceModule {
    private const val TOKEN_PUBLIC_KEY = "dG9rZW4="

    fun serviceProvider(context: Context) = Module("RESTFul Service") {
        // *** For the [Remote] data module.
        import(netProvider(context))
        import(firebaseProvider())
        import(retrofitConfigProvider())

        import(implementationRemoteProvider())

        // *** For the [Local] data module.
//        import(tensorFlowProvider(context))

        import(implementationLocalProvider(context))
    }

    //region Remote Providers
    /**
     * To provide the necessary objects [FirebaseDatabase] into the repository.
     */
    private fun firebaseProvider() = Module("Firebase") {
        bind<FirebaseDatabase>() with instance(FirebaseDatabase.getInstance())
    }

    /**
     * To provide the necessary objects [ApiConfig] into the repository.
     */
    private fun retrofitConfigProvider() = Module("Retrofit Configuration") {
        bind<LastFmConfig>() with instance(RestfulApiFactory().createLastFmConfig())
    }

    /**
     * To provide the necessary objects Remote Implementation into the repository.
     */
    private fun implementationRemoteProvider() = Module("Implementation Remote") {
        bind<LastFmService>() with singleton {
            with(instance<Retrofit.Builder>()) {
                baseUrl(instance<LastFmConfig>().apiBaseUrl)
                build()
            }.create(LastFmService::class.java)
        }
//        bind<NewsFirebase>() with singleton { NewsFirebaseImpl(instance()) }
    }
    //endregion

    //region Local Providers
    /**
     * To provide the necessary objects Local Implementation objects into the repository.
     */
    private fun implementationLocalProvider(context: Context) = Module("Implementation Local") {
        bind<MusicDatabase>() with instance(MusicDatabase.getDatabase(context))
        bind<MMKV>() with singleton { defaultMMKV(SINGLE_PROCESS_MODE, TOKEN_PUBLIC_KEY) }
    }
    //endregion
}
