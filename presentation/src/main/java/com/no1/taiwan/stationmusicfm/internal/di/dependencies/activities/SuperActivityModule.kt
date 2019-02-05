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

package com.no1.taiwan.stationmusicfm.internal.di.dependencies.activities

import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.features.test.TestViewModel
import com.no1.taiwan.stationmusicfm.internal.di.PreziMapperEntries
import com.no1.taiwan.stationmusicfm.internal.di.ViewModelEntry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

/**
 * To provide the necessary objects for the specific activities.
 */
object SuperActivityModule {
    fun activityModule() = Kodein.Module("All Activities") {
        // Import all of the activity modules.
        import(TestModule.testProvider())

        /** ViewModel Set for [com.no1.taiwan.stationmusicfm.utils.viewmodel.ViewModelFactory] */
        bind() from setBinding<ViewModelEntry>()
        /** Mapper Pool for providing all data mappers */
        bind<PreziMapperPool>() with singleton { instance<PreziMapperEntries>().toMap() }

        import(providerViewModel())
    }

    private fun providerViewModel() = Kodein.Module("Activity ViewModel") {
        // *** ViewModel
        bind<ViewModelEntry>().inSet() with provider {
            TestViewModel::class.java to TestViewModel(instance(), instance())
        }
    }
}
