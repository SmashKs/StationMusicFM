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

package com.no1.taiwan.stationmusicfm.internal.di.dependencies.fragments

import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreAlbumViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreArtistViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreGenreViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreIndexViewModel
import com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels.ExploreTrackViewModel
import com.no1.taiwan.stationmusicfm.features.main.search.SearchViewModel
import com.no1.taiwan.stationmusicfm.internal.di.ViewModelEntry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * To provide the necessary objects for the specific fragments.
 */
object SuperFragmentModule {
    fun fragmentModule() = Kodein.Module("All Fragments") {
        // Import all of the fragment modules.
        import(providerViewModel())
    }

    private fun providerViewModel() = Kodein.Module("Viewmodel Module") {
        // *** ViewModel
        bind<ViewModelEntry>().inSet() with provider {
            ExploreIndexViewModel::class.java to ExploreIndexViewModel(instance(),
                                                                       instance(),
                                                                       instance(),
                                                                       instance())
        }
        bind<ViewModelEntry>().inSet() with provider {
            ExploreAlbumViewModel::class.java to ExploreAlbumViewModel(instance(), instance())
        }
        bind<ViewModelEntry>().inSet() with provider {
            ExploreArtistViewModel::class.java to ExploreArtistViewModel(instance(),
                                                                         instance(),
                                                                         instance(),
                                                                         instance(),
                                                                         instance())
        }
        bind<ViewModelEntry>().inSet() with provider {
            ExploreGenreViewModel::class.java to ExploreGenreViewModel(instance(),
                                                                       instance(),
                                                                       instance(),
                                                                       instance(),
                                                                       instance())
        }
        bind<ViewModelEntry>().inSet() with provider {
            ExploreTrackViewModel::class.java to ExploreTrackViewModel(instance(), instance(), instance())
        }
        bind<ViewModelEntry>().inSet() with provider {
            SearchViewModel::class.java to SearchViewModel(instance(), instance())
        }

    }
}
