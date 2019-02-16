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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinshaver.castOrNull
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.fragments.SuperFragmentModule
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.FRAGMENT_BUS_LONG_LIFE
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.FRAGMENT_BUS_SHORT_LIFE
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.BusFragLongerLifeRegister
import org.jetbrains.anko.support.v4.find
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.multiton

/**
 * The basic fragment is for the normal activity which prepares all necessary variables or functions.
 */
abstract class BaseFragment<out A : BaseActivity> : Fragment(), KodeinAware {
    override val kodein = Kodein.lazy {
        extend(parentKodein)
        /* fragment specific bindings */
        import(SuperFragmentModule.fragmentModule())
        bind<LifecycleObserver>(FRAGMENT_BUS_SHORT_LIFE) with multiton { fragment: Fragment ->
            BusFragLifeRegister(fragment)
        }
        bind<LifecycleObserver>(FRAGMENT_BUS_LONG_LIFE) with multiton { fragment: Fragment ->
            BusFragLongerLifeRegister(fragment)
        }
    }
    @Suppress("UNCHECKED_CAST")
    protected val parent
        get() = requireActivity() as A  // If there's no parent, forcing crashing the app.
    protected val appContext: Context by instance()
    private var rootView: View? = null
    private val parentKodein by kodein()

    //region Fragment lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Keep the instance data.
        retainInstance = true

        val localInflater = customTheme()?.let {
            // Create ContextThemeWrapper from the original Activity Context with the custom theme
            val contextThemeWrapper = ContextThemeWrapper(activity, it)
            // Clone the inflater using the ContextThemeWrapper
            inflater.cloneInContext(contextThemeWrapper)
        } ?: inflater

        // FIXED: https://www.zybuluo.com/kimo/note/255244
        // inflate the layout using the cloned inflater, not default inflater
        rootView ?: let { rootView = localInflater.inflate(provideInflateView(), null) }
        val parent = castOrNull<ViewGroup>(rootView?.parent)
        parent?.removeView(rootView)

        return rootView
    }

    /**
     * For initializing the view components and setting the listeners.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the title into the support action bar.
//        parent.setSupportActionBar(findOptional(R.id.tb_header))
        actionBarTitle()?.let { parent.supportActionBar?.title = it }
        // Before setting.
        viewComponentBinding()
        componentListenersBinding()
        // Action for customizing.
        rendered(savedInstanceState)
        // When the fragment has base_layout uid, it'll attach the function of hiding soft keyboard.
//        view.findOptional<View>(R.id.base_layout)?.clickedThenHideKeyboard()
    }
    //endregion

    /**
     * Set the parentView for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    @UiThread
    @LayoutRes
    protected abstract fun provideInflateView(): Int

    /**
     * For separating the huge function code in [rendered]. Initialize all view components here.
     */
    @UiThread
    protected open fun viewComponentBinding() = Unit

    /**
     * For separating the huge function code in [rendered]. Initialize all component listeners here.
     */
    @UiThread
    protected open fun componentListenersBinding() = Unit

    /**
     * Initialize doing some methods or actions here.
     *
     * @param savedInstanceState previous status.
     */
    @UiThread
    protected open fun rendered(savedInstanceState: Bundle?) = Unit

    /**
     * Set specific theme to this fragment.
     *
     * @return [Int] style xml resource.
     */
    @UiThread
    @StyleRes
    protected open fun customTheme(): Int? = null

    /**
     * Set fragment title into action bar.
     *
     * @return [String] action bar title.
     */
    @UiThread
    protected open fun actionBarTitle(): String? = null

    fun initRecyclerViewWith(
        @IdRes resRecyclerViewId: Int,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager,
        decoration: RecyclerView.ItemDecoration? = null
    ) {
        find<RecyclerView>(resRecyclerViewId).apply {
            if (this.layoutManager == null)
                this.layoutManager = layoutManager
            if (this.adapter == null)
                this.adapter = adapter
            if (itemDecorationCount == 0 && decoration != null)
                addItemDecoration(decoration)
        }
    }

}
