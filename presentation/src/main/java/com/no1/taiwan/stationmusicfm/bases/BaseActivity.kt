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
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

/**
 * The basic activity is for the normal activity which prepares all necessary variables or functions.
 */
abstract class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by retainedKodein {
        extend(parentKodein)
//        import(appProvider())
        /** activities or fragments */
//        import(presentationUtilProvider(this@BaseActivity))
//        import(recyclerViewProvider())
        /* activity specific bindings */
//        import(activityModule())

//        bind<ViewModelProvider.Factory>() with provider {
//            ViewModelFactory(instance(), instance<ViewModelEntries>().toMap().toMutableMap())
//        }
    }
    private val parentKodein by closestKodein()

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSetContentView()
        setContentView(provideLayoutId())
        // Pre-set the view components.
        viewComponentBinding()
        componentListenersBinding()
        init(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        uninit()
    }
    //endregion

    /**
     * Initialize doing some methods and actions.
     *
     * @param savedInstanceState previous state after this activity was destroyed.
     */
    @UiThread
    protected fun init(savedInstanceState: Bundle?) = Unit

    @LayoutRes
    abstract fun provideLayoutId(): Int

    /**
     * For separating the huge function code in [init]. Initialize all view components here.
     */
    @UiThread
    protected open fun viewComponentBinding() = Unit

    /**
     * For separating the huge function code in [init]. Initialize all component listeners here.
     */
    @UiThread
    protected open fun componentListenersBinding() = Unit

    @UiThread
    protected open fun uninit() = Unit

    @UiThread
    protected open fun preSetContentView() = Unit
}
