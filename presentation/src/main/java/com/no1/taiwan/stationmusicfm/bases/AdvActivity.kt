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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.devrapid.dialogbuilder.support.QuickDialogFragment
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.kits.view.hideRetryView
import com.no1.taiwan.stationmusicfm.kits.view.showErrorView
import com.no1.taiwan.stationmusicfm.kits.view.showLoadingView
import com.no1.taiwan.stationmusicfm.kits.view.showRetryView
import com.no1.taiwan.stationmusicfm.widget.components.dialog.LoadingDialog
import org.kodein.di.generic.instance
import java.lang.reflect.ParameterizedType

/**
 * The basic activity is in MVVM architecture which prepares all necessary variables or functions.
 */
abstract class AdvActivity<out VM : ViewModel> : BaseActivity(), LoadView {
    /** Add the AAC [ViewModel] for each fragments. */
    @Suppress("UNCHECKED_CAST")
    protected val vm
        get() = vmCreateMethod.invoke(vmProviders, vmConcreteClass) as? VM ?: throw ClassCastException()

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    /** [VM] is the first (index: 0) in the generic declare. */
    private val vmConcreteClass
        get() = cast<Class<*>>(cast<ParameterizedType>(this::class.java.genericSuperclass).actualTypeArguments[0])
    private val vmProviders get() = ViewModelProviders.of(this, viewModelFactory)
    /** The [ViewModelProviders.of] function for obtaining a [ViewModel]. */
    private val vmCreateMethod get() = vmProviders.javaClass.getMethod("get", vmConcreteClass.superclass.javaClass)
    /** Dialog loading view. */
    private val loadingView by lazy { LoadingDialog.getInstance(this) }
    /** Enable dialog loading view or use loading layout. */
    protected open var enableDialogLoading = true

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    override fun showLoading() =
        if (enableDialogLoading) loadingView?.show() ?: Unit else showLoadingView()

    /**
     * Hide a loading view.
     */
    override fun hideLoading() = if (enableDialogLoading)
        loadingView?.takeUnless(QuickDialogFragment::isDismiss)?.dismissAllowingStateLoss() ?: Unit
    else
        showLoadingView()

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    override fun showRetry() = showRetryView()

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    override fun hideRetry() = hideRetryView()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    override fun showError(message: String) = showErrorView(message)
}
