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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinknifer.changeColor
import com.devrapid.kotlinknifer.extra
import com.devrapid.kotlinknifer.getColor
import com.devrapid.kotlinknifer.toDrawable
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.internal.di.dependencies.fragments.SuperFragmentModule
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.FRAGMENT_BUS_LONG_LIFE
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.FRAGMENT_BUS_SHORT_LIFE
import com.no1.taiwan.stationmusicfm.utils.FragmentArguments.COMMON_TITLE
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLifeRegister
import com.no1.taiwan.stationmusicfm.utils.aac.lifecycles.BusFragLongerLifeRegister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.kodein.di.KodeinAware
import org.kodein.di.android.subKodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.multiton

/**
 * The basic fragment is for the normal activity that prepares all necessary variables or functions.
 */
abstract class BaseFragment<out A : BaseActivity> : Fragment(),
                                                    KodeinAware,
                                                    CoroutineScope by MainScope() {
    override val kodein by subKodein(kodein()) {
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
        // If there's no parent, forcing crashing the app.
        get() = requireActivity() as A
    // Set action bar's back icon color into all fragments are inheriting advFragment.
    protected open val backDrawable by lazy {
        R.drawable.ic_arrow_back_black.toDrawable(parent)
            .changeColor(getColor(R.color.colorPrimaryTextV1))
    }

    private val actionTitle by extra<String>(COMMON_TITLE)

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        // Focusing on entering animation ([enter] == true) with animation ([nextAnim] != 0).
        if (!enter || nextAnim == 0) return super.onCreateAnimation(transit, enter, nextAnim)
        val anim = AnimationUtils.loadAnimation(activity, nextAnim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                onTransitionStart(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                onTransitionRepeat(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                onTransitionEnd(animation)
                anim.setAnimationListener(null)
            }
        })

        return anim
    }

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

        //region FIXED: Because of using jetpack with MVVM architecture, so we don't need this one for keeping old view.
        //              If we're still keeping the old view, it will cause when phone rotates the view will crashing.
        // FIXED: https://www.zybuluo.com/kimo/note/255244
        // inflate the layout using the cloned inflater, not default inflater.
//        rootView ?: let {
//        rootView = localInflater.inflate(provideInflateView(), null)
//        }
//        val parent = castOrNull<ViewGroup>(rootView?.parent)
//        parent?.removeView(rootView)
        //endregion

        return localInflater.inflate(provideInflateView(), null)
    }

    /**
     * For initializing the view components and setting the listeners.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Before setting.
        viewComponentBinding()
        componentListenersBinding()
        // Action for customizing.
        rendered(savedInstanceState)
        // When the fragment has base_layout uid, it'll attach the function of hiding soft keyboard.
//        view.findOptional<View>(R.id.base_layout)?.clickedThenHideKeyboard()
    }
    //endregion

    //region Customized methods
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
    protected open fun viewComponentBinding() {
        if (parent.supportActionBar == null) {
            // Set the title into the support action bar.
            parent.setSupportActionBar(provideActionBarResource())
        }
        parent.supportActionBar?.apply {
            actionBarTitle()?.let(this::setTitle)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(backDrawable)
        }
    }

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
    protected open fun actionBarTitle(): CharSequence? = actionTitle

    /**
     * Provide action bar object for pre-setting.
     *
     * @return [Toolbar] action bar object.
     */
    @UiThread
    protected open fun provideActionBarResource(): Toolbar? = null

    /**
     * The event for starting the view transition animation.
     *
     * @param animation
     */
    @UiThread
    protected open fun onTransitionStart(animation: Animation) = Unit

    /**
     * The event for repeating the view transition animation.
     *
     * @param animation
     */
    @UiThread
    protected open fun onTransitionRepeat(animation: Animation) = Unit

    /**
     * The event for ending the view transition animation.
     *
     * @param animation
     */
    @UiThread
    protected open fun onTransitionEnd(animation: Animation) = Unit
    //endregion

    @UiThread
    open fun onBackPressed() = Unit

    /**
     * Initialize the recycler view with all simple parameters.
     *
     * @param recyclerView the recycler view object.
     * @param adapter the adapter of the recycler view.
     * @param layoutManager the layout manager of the recycler view.
     * @param decoration the decoration of the recycle view.
     */
    fun initRecyclerViewWith(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager,
        decoration: RecyclerView.ItemDecoration?
    ) {
        recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            decoration?.let(::addItemDecoration)
        }
    }

    /**
     * Initialize the recycler view with all simple parameters.
     *
     * @param recyclerView the recycler view object.
     * @param adapter the adapter of the recycler view.
     * @param layoutManager the layout manager of the recycler view.
     * @param decorations a list of decorations of the recycle view.
     */
    fun initRecyclerViewWith(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager,
        decorations: List<RecyclerView.ItemDecoration> = emptyList()
    ) {
        recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            decorations.forEach(::addItemDecoration)
        }
    }
}
