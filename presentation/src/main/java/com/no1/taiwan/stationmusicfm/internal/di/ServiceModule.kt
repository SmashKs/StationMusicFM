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

package com.no1.taiwan.stationmusicfm.internal.di

import android.content.Context
import com.no1.taiwan.stationmusicfm.data.local.config.MusicDatabase
import com.no1.taiwan.stationmusicfm.data.remote.RestfulApiFactory
import com.no1.taiwan.stationmusicfm.data.remote.config.LastFmConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.MusicConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.MusicSeekerConfig
import com.no1.taiwan.stationmusicfm.data.remote.config.RankingConfig
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmExtraService
import com.no1.taiwan.stationmusicfm.data.remote.services.LastFmService
import com.no1.taiwan.stationmusicfm.data.remote.services.MusicBankService
import com.no1.taiwan.stationmusicfm.data.remote.services.SeekerBankService
import com.no1.taiwan.stationmusicfm.data.remote.v1.LastFmExtraImpl
import com.no1.taiwan.stationmusicfm.data.remote.v1.SeekerBankImpl
import com.no1.taiwan.stationmusicfm.internal.di.NetModule.netProvider
import com.no1.taiwan.stationmusicfm.internal.di.tags.InstanceTag.JSOUP
import com.no1.taiwan.stationmusicfm.internal.di.tags.InstanceTag.RETROFIT
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
        //        bind<FirebaseDatabase>() with instance(FirebaseDatabase.getInstance())
    }

    /**
     * To provide the necessary objects [com.no1.taiwan.stationmusicfm.data.remote.config.ApiConfig]
     * into the repository.
     */
    private fun retrofitConfigProvider() = Module("Retrofit Configuration") {
        bind<LastFmConfig>() with instance(RestfulApiFactory().createLastFmConfig())
        bind<MusicSeekerConfig>() with instance(RestfulApiFactory().createMusicSeekerConfig())
        bind<RankingConfig>() with instance(RestfulApiFactory().createRankingConfig())
        bind<MusicConfig>() with instance(RestfulApiFactory().createMusicConfig())
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
        bind<LastFmExtraService>() with instance(LastFmExtraImpl())
        bind<MusicBankService>() with singleton {
            with(instance<Retrofit.Builder>()) {
                baseUrl(instance<RankingConfig>().apiBaseUrl)
                build()
            }.create(MusicBankService::class.java)
        }
        bind<SeekerBankService>(RETROFIT) with singleton {
            with(instance<Retrofit.Builder>()) {
                baseUrl(instance<MusicSeekerConfig>().apiBaseUrl)
                build()
            }.create(SeekerBankService::class.java)
        }
        bind<SeekerBankService>(JSOUP) with instance(SeekerBankImpl())
//        bind<NewsFirebase>() with singleton { NewsFirebaseImpl(instance()) }
    }
    //endregion

    //region Local Providers
    /**
     * To provide the necessary objects Local Implementation objects into the repository.
     */
    private fun implementationLocalProvider(context: Context) = Module("Implementation Local") {
        bind<MusicDatabase>() with instance(MusicDatabase.getDatabase(context))
        // bind<MMKV>() with singleton { defaultMMKV(SINGLE_PROCESS_MODE, TOKEN_PUBLIC_KEY) }
    }
    //endregion
}
