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

package dependencies

/**
 * Collect all libs of the version number.
 */
object Versions {
    /**
     * Gradle android version.
     */
    object Android {
        const val minSdk = 21
        const val targetSdk = 28
        const val buiildTool = "28.0.3"
        const val compileSdk = targetSdk
    }

    /**
     * Related Android component lib version.
     */
    object AndroidComponent {
        const val material = "1.1.0-alpha05"
        const val androidx = "1.0.0"
        const val annotation = "1.1.0-beta01"
        const val appCompat = "1.1.0-alpha04"
        const val cardView = androidx
        const val recyclerView = appCompat
        const val constraintLayout = "2.0.0-alpha4"
        const val coordinatorLayout = "1.1.0-alpha01"
    }

    /**
     * Related Kotlin lib version.
     */
    object Kotlin {
        const val kotlinLib = "1.3.30"
        const val kotlinCoroutine = "1.2.0"
    }

    /**
     * Related view component lib version.
     */
    object ViewComponent {
        const val adaptiveRecyclerView = "1.0.16"
        const val shapeOfView = "1.4.7"
        const val realtimeBlur = "1.1.2"
        const val dialog = "1.0.7"
        const val lottie = "3.0.0"
    }

    /**
     * Related Kotlin extensions lib version.
     */
    object KotlinAndroidExt {
        const val dex = "2.0.1"
        const val anko = "0.10.8"
        const val kinfer = "2.1.10"
        const val ktx = "1.1.0-alpha05"
        const val fragmentKtx = "1.1.0-alpha06"
        const val paletteKtx = "1.0.0"
        const val collectionKtx = "1.1.0-beta01"
        const val viewmodelKtx = AndroidArchitectureComponent.aacLifecycle
        const val navigationKtx = "2.1.0-alpha02"
        const val workKtx = "2.0.1"
        const val dynAnimKtx = "1.0.0-alpha01"
    }

    object AndroidArchitectureComponent {
        const val aacLifecycle = "2.1.0-alpha04"
        const val room = "2.1.0-alpha06"
    }

    /**
     * Related dependency injection lib version.
     */
    object DI {
        const val kodein = "6.2.0"
    }

    /**
     * Related Firebase lib version.
     */
    object Firebase {
        const val core = "16.0.8"
        const val database = "16.1.0"
        const val auth = "16.0.3"
        const val messaging = "17.5.0"

        const val googleService = "4.2.0"
    }

    object CloudStore {
        const val cloudinary = "1.24.1"
    }

    /**
     * Related database lib version.
     */
    object Database {
        const val debug = "1.5.1"
        const val debugDb = "1.0.6"
        const val mmkv = "1.0.18"
    }

    /**
     * Related network lib version.
     */
    object Network {
        const val glide = "4.9.0"
        const val retrofit2 = "2.5.0"
        const val adapterCoroutine = "0.9.2"
        const val okhttp3 = "3.14.1"
        const val okhttpProfiler = "1.0.5"
        const val activityLauncher = "1.0.2"
        const val jsoup = "1.11.3"
    }

    /**
     * Related reactive lib version.
     */
    object RxDep {
        const val rxJava2 = "2.2.1"
        const val rxKotlin2 = "2.3.0"
        const val rxPermission2 = "0.9.5@aar"
        const val rxBus = "2.0.1"
    }

    /**
     * Related parser lib version.
     */
    object Parser {
        const val gson = "2.8.5"
        const val jsoup = "1.10.3"
    }

    /**
     * Related Android unit test lib version.
     */
    object Test {
        const val jUnit = "1.1.0"
        const val assertK = "0.13"
        const val runner = "1.1.1"
        const val espresso = "3.1.1"
        const val kakao = "2.0.0"
        const val powerMockito = "1.7.4"
        const val mockitoKotlin = "2.1.0"
        const val mockitoAndroid = "2.23.0"
        const val mockk = "v1.8.9.kotlin13"
        const val byteBuddy = "1.9.12"
    }

    /**
     * Related extension Plugins lib version.
     */
    object Plugin {
        const val detekt = "1.0.0-RC14"
        const val versionUpdater = "0.20.0"
    }

    object Player {
        const val exoplayer = "2.9.6"
    }
}
