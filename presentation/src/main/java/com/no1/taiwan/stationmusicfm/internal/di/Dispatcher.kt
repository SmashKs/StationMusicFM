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

import android.app.Application
import android.content.Context
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.UsecaseModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.DataMapperModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.PresentationMapperModule
import org.kodein.di.Kodein
import org.kodein.di.android.x.androidXModule

object Dispatcher {
    fun importIntoApp(app: Application) = Kodein.lazy {
        import(androidXModule(app))
        import(AppModule.appProvider(app.applicationContext))
        /** bindings */
        import(UtilModule.utilProvider(app.applicationContext))
        /** usecases are bind here but the scope is depending on each layers.  */
        import(UsecaseModule.usecaseProvider())
        import(UsecaseModule.delegateProvider())
        import(RepositoryModule.repositoryProvider(app.applicationContext))
        import(DataMapperModule.dataUtilProvider())
    }

    fun importIntoService(context: Context) = Kodein.lazy {
        /** bindings */
        import(UtilModule.utilProvider(context))
        import(PresentationModule.kitsProvider())
        /** usecases are bind here but the scope is depending on each layers.  */
        import(UsecaseModule.usecaseProvider())
        import(RepositoryModule.repositoryProvider(context))
        import(DataMapperModule.dataUtilProvider())
        import(PresentationMapperModule.presentationUtilProvider())
    }

    // TODO(jieyi): 2019-04-10 Separate more for detail.
    fun importIntoBottomSheet(context: Context) = Kodein.lazy {
        import(AppModule.appProvider(context.applicationContext))
        /** bindings */
        import(UtilModule.utilProvider(context))
        import(PresentationModule.kitsProvider())
        /** usecases are bind here but the scope is depending on each layers.  */
        import(UsecaseModule.usecaseProvider())
        import(RepositoryModule.repositoryProvider(context))
        import(DataMapperModule.dataUtilProvider())
        import(PresentationMapperModule.presentationUtilProvider())
        /**  */
        import(RecyclerViewModule.recyclerViewProvider())
    }
}
