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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNull
import com.google.android.material.snackbar.Snackbar
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.ext.DEFAULT_INT
import com.no1.taiwan.stationmusicfm.ext.isDefault
import com.no1.taiwan.stationmusicfm.kits.view.hideLoadingView
import com.no1.taiwan.stationmusicfm.kits.view.hideRetryView
import com.no1.taiwan.stationmusicfm.kits.view.showErrorView
import com.no1.taiwan.stationmusicfm.kits.view.showLoadingView
import com.no1.taiwan.stationmusicfm.kits.view.showRetryView
import com.no1.taiwan.stationmusicfm.widget.components.dialog.LoadingDialog
import org.jetbrains.anko.findOptional
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
        const val PROVIDER_FROM_CUSTOM_FRAGMENT = 0x3
    }

    protected open val genericVMIndex = DEFAULT_INT
    protected open val viewmodelProviderSource = PROVIDER_FROM_FRAGMENT
    /** Only [viewmodelProviderSource] is [PROVIDER_FROM_CUSTOM_FRAGMENT] needs to set. */
    protected open val viewmodelProviderFragment: Fragment? = null
    /** Add the AAC [ViewModel] for each fragments. */
    @Suppress("UNCHECKED_CAST")
    protected val vm by lazy {
        vmCreateMethod.invoke(vmProvider(viewmodelProviderSource), vmConcreteClass) as? VM ?: throw ClassCastException()
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
        vmProvider(viewmodelProviderSource).javaClass.getMethod("get", vmConcreteClass.superclass.javaClass)
    }
    /** Dialog loading view. */
    private val loadingView by lazy { LoadingDialog.getInstance(this, true) }
    /** Enable dialog loading view or use loading layout. */
    protected open var enableDialogLoading = true
    protected open var firstTimeEnter = true

    //region Fragment's lifecycle.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindLiveData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    //endregion

    //region View Implementation for the Presenter.
    @UiThread
    override fun showLoading() = if (enableDialogLoading)
        loadingView?.takeIf { it.tag.isNull() }?.show() ?: Unit
    else
        parent.showLoadingView()

    @UiThread
    override fun hideLoading() = if (enableDialogLoading) loadingView?.dialog?.dismiss() ?: Unit
    else
        parent.hideLoadingView()

    @UiThread
    override fun showRetry() = parent.showRetryView()

    @UiThread
    override fun hideRetry() = parent.hideRetryView()

    @UiThread
    override fun showError(message: String) = if (enableDialogLoading)
        view?.let { Snackbar.make(it, "Error happened!", Snackbar.LENGTH_SHORT).show() } ?: Unit
    else
        parent.showErrorView(message)
    //endregion

    /** The block of binding to [androidx.lifecycle.ViewModel]'s [androidx.lifecycle.LiveData]. */
    @UiThread
    protected open fun bindLiveData() = Unit

    /**
     * Provide action bar object for pre-setting. This app's default action bar is base on
     * [androidx.appcompat.app.ActionBar] [R.id.tb_header].
     *
     * @return [Toolbar] action bar object.
     */
    override fun provideActionBarResource() = parent.findOptional<Toolbar>(R.id.tb_header)

    protected fun vmProvider(providerFrom: Int) = when (providerFrom) {
        PROVIDER_FROM_ACTIVITY -> ViewModelProviders.of(requireActivity(), viewModelFactory)
        PROVIDER_FROM_FRAGMENT -> ViewModelProviders.of(this, viewModelFactory)
        PROVIDER_FROM_CUSTOM_FRAGMENT -> if (viewmodelProviderFragment != null)
            ViewModelProviders.of(requireNotNull(viewmodelProviderFragment), viewModelFactory)
        else
            ViewModelProviders.of(this, viewModelFactory)
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
