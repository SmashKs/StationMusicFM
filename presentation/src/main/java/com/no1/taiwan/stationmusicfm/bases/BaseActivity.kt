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

package com.no1.taiwan.stationmusicfm.bases

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.no1.taiwan.stationmusicfm.internal.di.RecyclerViewModule
import com.no1.taiwan.stationmusicfm.internal.di.ViewModelEntries
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.activities.SuperActivityModule
import com.no1.taiwan.stationmusicfm.internal.di.mappers.PresentationMapperModule
import com.no1.taiwan.stationmusicfm.widget.components.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * The basic activity is for the normal activity that prepares all necessary variables or functions.
 */
abstract class BaseActivity : AppCompatActivity(), KodeinAware, CoroutineScope by MainScope() {
    override val kodein by retainedKodein {
        extend(parentKodein)
//        import(appProvider())
        /** activities or fragments */
        import(PresentationMapperModule.presentationUtilProvider())
        import(RecyclerViewModule.recyclerViewProvider())
        /* activity specific bindings */
        import(SuperActivityModule.activityModule())

        bind<ViewModelProvider.Factory>() with provider {
            ViewModelFactory(instance(), instance<ViewModelEntries>().toMap().toMutableMap())
        }
    }
    private val parentKodein by kodein()

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pre-set the view components.
        preSetContentView()
        setContentView(provideLayoutId())
        // Post-set the view components.
        viewComponentBinding()
        componentListenersBinding()
        init(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        uninit()
        cancel() // cancel is extension on CoroutineScope
    }
    //endregion

    @LayoutRes
    abstract fun provideLayoutId(): Int

    /**
     * Initialize doing some methods and actions.
     *
     * @param savedInstanceState previous state after this activity was destroyed.
     */
    @UiThread
    protected open fun init(savedInstanceState: Bundle?) = Unit

    /**
     * For separating the huge function code in [init]. Initialize all view components here before [setContentView].
     */
    @UiThread
    protected open fun preSetContentView() = Unit

    /**
     * For separating the huge function code in [init]. Initialize all view components here after [setContentView].
     */
    @UiThread
    protected open fun viewComponentBinding() = Unit

    /**
     * For separating the huge function code in [init]. Initialize all component listeners here.
     */
    @UiThread
    protected open fun componentListenersBinding() = Unit

    /**
     * Uninit the data or deconstruct objects.
     */
    @UiThread
    protected open fun uninit() = Unit
}
