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

package com.no1.taiwan.stationmusicfm.features.main.explore.viewmodels

import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopArtistCase
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.utils.aac.AutoViewModel

class ExploreGenreViewModel(
    private val fetchTagCase: FetchTagCase,
    private val fetchTagTopArtistCase: FetchTagTopArtistCase,
    private val fetchArtistTopAlbumCase: FetchArtistTopAlbumCase,
    private val fetchArtistTopTrackCase: FetchArtistTopTrackCase,
    private val mapperPool: PreziMapperPool
) : AutoViewModel()
