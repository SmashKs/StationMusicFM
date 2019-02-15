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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.devrapid.kotlinshaver.cast
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.isDefault
import com.no1.taiwan.stationmusicfm.internal.di.BaseFragment
import org.kodein.di.generic.instance
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * The basic fragment is in MVVM architecture which prepares all necessary variables or functions.
 */
abstract class AdvFragment<out A : BaseActivity, out VM : ViewModel> : BaseFragment<A>(), LoadView {
    companion object {
        const val PROVIDER_FROM_ACTIVITY = 0x1
        const val PROVIDER_FROM_FRAGMENT = 0x2
    }

    protected open val genericVMIndex = DEFAULT_INT
    /** Add the AAC [ViewModel] for each fragments. */
    @Suppress("UNCHECKED_CAST")
    protected val vm by lazy {
        vmCreateMethod.invoke(vmProvider(PROVIDER_FROM_FRAGMENT), vmConcreteClass) as? VM ?: throw ClassCastException()
    }
    // OPTIMIZE(jieyi): 2019/02/15 In old phone here will cause frame drops.
    private val viewModelFactory: ViewModelProvider.Factory by instance()
    /** [VM] is the first (index: 1) in the generic declare. */
    private val vmConcreteClass: Class<*>
        get() {
            // Get the all generic data types.
            val actualTypeArguments =
                cast<ParameterizedType>(recursiveFindGenericSuperClass(this::class.java)).actualTypeArguments

            // If we don't set viewmodel index by ourselves, it can find the first generic viewmodel.
            val viewmodelClass = if (genericVMIndex.isDefault())
            // Recursively find the first generic viewmodel data type.
                actualTypeArguments.firstOrNull { checkAllSuperClass(cast(it), ViewModel::class.java) }
            else
            // Customize index.
                actualTypeArguments[genericVMIndex]

            return cast(viewmodelClass)
        }
    /** The [ViewModelProviders.of] function for obtaining a [ViewModel]. */
    private val vmCreateMethod by lazy {
        vmProvider(PROVIDER_FROM_FRAGMENT).javaClass.getMethod("get", vmConcreteClass.superclass.javaClass)
    }
    /** Dialog loading view. */
//    private val loadingView by lazy { LoadingDialog.getInstance(this, true) }
    /** Enable dialog loading view or use loading layout. */
    protected open var enableDialogLoading = true

    //region Fragment's lifecycle.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindLiveData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    //endregion

    //region View Implementation for the Presenter.
    @UiThread
    override fun showLoading() = Unit
//        if (enableDialogLoading) loadingView.show() else parent.showLoadingView()

    @UiThread
    override fun hideLoading() = Unit
//        if (enableDialogLoading)
//            loadingView.takeUnless(QuickDialogFragment::isDismiss)?.dismiss() ?: Unit
//        else
//            parent.hideLoadingView()

    @UiThread
    override fun showRetry() = Unit
//        parent.showRetryView()

    @UiThread
    override fun hideRetry() = Unit
//        parent.hideRetryView()

    @UiThread
    override fun showError(message: String) = Unit
//        parent.showErrorView(message)
    //endregion

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    @UiThread
    protected open fun bindLiveData() = Unit

    protected fun vmProvider(providerFrom: Int) = when (providerFrom) {
        PROVIDER_FROM_ACTIVITY -> ViewModelProviders.of(requireActivity(), viewModelFactory)
        PROVIDER_FROM_FRAGMENT -> ViewModelProviders.of(this, viewModelFactory)
        else -> throw IllegalArgumentException()
    }

    private fun recursiveFindGenericSuperClass(superclass: Class<*>): Type =
        if (superclass.genericSuperclass is ParameterizedType)
            requireNotNull(superclass.genericSuperclass)
        else
            recursiveFindGenericSuperClass(requireNotNull(superclass.superclass))

    private fun checkAllSuperClass(objClass: Class<*>, assignable: Class<*>): Boolean {
        objClass.superclass?.takeUnless { it.isAssignableFrom(java.lang.Object::class.java) }?.let {
            return if (it.isAssignableFrom(assignable))
                true
            else
                checkAllSuperClass(it, assignable)
        } ?: return false
    }
}
