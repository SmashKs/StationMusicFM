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

object Dependencies {
    private val basicKotlin = hashMapOf(
        "kotlin" to Deps.Global.kotlin,
        "reflect" to Deps.Global.reflect
    )

    val kotlinAndroid = basicKotlin.apply {
        put("coroutineForAndroid", Deps.Presentation.androidCoroutine)
    }

    val kotlin = basicKotlin.apply {
        put("coroutine", Deps.Global.coroutine)
    }

    val commonAndroidx = hashMapOf(
        "appcompat" to Deps.Presentation.appcompat,
        "coordinatorLayout" to Deps.Presentation.coordinatorLayout,
        "lifecycle" to Deps.Presentation.lifecycle
    )

    val preziAndroidx = hashMapOf(
        "dexTool" to Deps.Presentation.dexTool,
        "materialDesign" to Deps.Presentation.materialDesign,
        "constraintLayout" to Deps.Presentation.constraintLayout,
        "recyclerview" to Deps.Presentation.recyclerview,
        "cardview" to Deps.Presentation.cardview,
        "annot" to Deps.Presentation.annot
    )

    val preziAnko = hashMapOf(
        "anko" to Deps.Presentation.anko,
        "ankoSdk25" to Deps.Presentation.ankoSdk25,
        "ankoV7" to Deps.Presentation.ankoV7,
        "ankoCoroutine" to Deps.Presentation.ankoCoroutine,
        "ankoV7Coroutine" to Deps.Presentation.ankoV7Coroutine,
        "ankoCoroutines" to Deps.Presentation.ankoCoroutines
    )

    val preziKtx = hashMapOf(
        "ktx" to Deps.Presentation.ktx,
        "fragmentKtx" to Deps.Presentation.fragmentKtx,
        "paletteKtx" to Deps.Presentation.paletteKtx,
        "collectionKtx" to Deps.Presentation.collectionKtx,
        "viewmodelKtx" to Deps.Presentation.viewmodelKtx,
        "livedataKtx" to Deps.Presentation.livedataKtx,
        "runtimeKtx" to Deps.Presentation.runtimeKtx,
        "navigationCommonKtx" to Deps.Presentation.navigationCommonKtx,
        "navigationFragmentKtx" to Deps.Presentation.navigationFragmentKtx,
        "navigationUiKtx" to Deps.Presentation.navigationUiKtx,
        "workerKtx" to Deps.Presentation.workerKtx
//        "dynAnimKtx" to Deps.Presentation.dynAnimKtx
    )

    val player = hashMapOf(
        "exoplayer" to Deps.Exoplayer.exoplayer,
        "exoplyerUi" to Deps.Exoplayer.exoplyerUi
    )

    val koin = hashMapOf(
        "koin" to Deps.Global.koin,
        "koinScope" to Deps.Global.koinScope,
        "koinVM" to Deps.Global.koinVM,
        "koinExt" to Deps.Global.koinExt
    )
}
