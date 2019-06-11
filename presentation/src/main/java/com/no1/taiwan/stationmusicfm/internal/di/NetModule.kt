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
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.no1.taiwan.stationmusicfm.BuildConfig
import com.no1.taiwan.stationmusicfm.data.hasNetwork
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * To provide the necessary object for the internet model.
 */
object NetModule {
    private const val CacheMaxSize = 10 * 1024 * 1024L
    private const val AWeekTime = 60 * 60 * 24 * 7

    fun netProvider(context: Context) = Kodein.Module("Network") {
        bind<Converter.Factory>() with singleton { GsonConverterFactory.create(instance()) }
        bind<Cache>() with singleton { Cache(context.cacheDir, CacheMaxSize /* 10 MiB */) }
        bind<OkHttpClient.Builder>() with singleton {
            OkHttpClient.Builder()
                .cache(instance())
                // Keep the internet result into the cache.
                .addInterceptor {
                    // Get the request from the chain.
                    var request = it.request()

                    /**
                     *  Leveraging the advantage of using Kotlin,
                     *  we initialize the request and change its header depending on whether
                     *  the device is connected to Internet or not.
                     */
                    request = if (hasNetwork(context) == true) {
                        /**
                         *  If there is Internet, get the cache that was stored 5 seconds ago.
                         *  If the cache is older than 5 seconds, then discard it,
                         *  and indicate an error in fetching the response.
                         *  The 'max-age' attribute is responsible for this behavior.
                         */
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    }
                    else {
                        /**
                         *  If there is no Internet, get the cache that was stored 7 days ago.
                         *  If the cache is older than 7 days, then discard it,
                         *  and indicate an error in fetching the response.
                         *  The 'max-stale' attribute is responsible for this behavior.
                         *  The 'only-if-cached' attribute indicates to not retrieve add data; fetch the cache
                         *  only instead.
                         */
                        request.newBuilder().header("Cache-Control",
                                                    "public, only-if-cached, max-stale=$AWeekTime").build()
                    }
                    // End of if-else statement

                    // Add the modified request to the chain.
                    it.proceed(request)
                }
                .apply {
                    if (BuildConfig.DEBUG) {
//                        addInterceptor(HttpLoggingInterceptor().setLevel(BODY))  // For print to logcat.
                        addInterceptor(OkHttpProfilerInterceptor())  // For OkHttp Profiler plugins.
                        addNetworkInterceptor(StethoInterceptor())
                    }
                    // Those three are for HTTPS protocol.
                    connectionSpecs(mutableListOf(ConnectionSpec.RESTRICTED_TLS,
                                                  ConnectionSpec.MODERN_TLS,
                                                  ConnectionSpec.COMPATIBLE_TLS,
                        // This is for HTTP protocol.
                                                  ConnectionSpec.CLEARTEXT))
                }
        }
        bind<Retrofit.Builder>() with singleton {
            Retrofit.Builder()
                .addConverterFactory(instance())
                .client(instance<OkHttpClient.Builder>().build())
        }
    }
}
