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

package com.no1.taiwan.stationmusicfm.internal.di.dependencies

import com.no1.taiwan.stationmusicfm.domain.usecases.AddLocalMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddPlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddRankIdsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.AddSearchHistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.DeletePlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.DeleteSearchHistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistPhotoCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchArtistTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchChartTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchHotListCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankIdsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchRankMusicCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSearchHistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSimilarTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchSongsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopAlbumCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopArtistCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTagTopTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchTrackCase
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdatePlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.UpdateRankItemCase
import com.no1.taiwan.stationmusicfm.domain.usecases.history.AddSearchHistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.history.DeleteSearchHistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.history.FetchSearchHistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistPhotoRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistTopAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchArtistTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopTagRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchChartTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchSimilarArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchSimilarTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopAlbumRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopArtistRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTagTopTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.lastfm.FetchTrackRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.AddRankIdsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchHotListRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankIdsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchRankMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.FetchSongsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.musicbank.UpdateRankItemRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.AddLocalMusicRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.AddPlaylistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.DeletePlaylistsRespCase
import com.no1.taiwan.stationmusicfm.domain.usecases.playlist.UpdatePlaylistsRespCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * To provide the necessary usecase objects for the specific activities or fragments.
 */
object UsecaseModule {
    fun usecaseProvider() = Kodein.Module("Use Cases") {
        //region For Fragments
        bind<FetchRankMusicCase>() with singleton { FetchRankMusicRespCase(instance()) }
        bind<FetchSongsCase>() with singleton { FetchSongsRespCase(instance()) }
        bind<FetchMusicCase>() with singleton { FetchMusicRespCase(instance()) }
        bind<FetchHotListCase>() with singleton { FetchHotListRespCase(instance()) }
        bind<AddRankIdsCase>() with singleton { AddRankIdsRespCase(instance()) }
        bind<FetchRankIdsCase>() with singleton { FetchRankIdsRespCase(instance()) }
        bind<UpdateRankItemCase>() with singleton { UpdateRankItemRespCase(instance()) }
        //endregion

        //region LastFm
        bind<FetchAlbumCase>() with singleton { FetchAlbumRespCase(instance()) }
        bind<FetchArtistCase>() with singleton { FetchArtistRespCase(instance()) }
        bind<FetchArtistPhotoCase>() with singleton { FetchArtistPhotoRespCase(instance()) }
        bind<FetchArtistTopAlbumCase>() with singleton { FetchArtistTopAlbumRespCase(instance()) }
        bind<FetchArtistTopTrackCase>() with singleton { FetchArtistTopTrackRespCase(instance()) }
        bind<FetchChartTopArtistCase>() with singleton { FetchChartTopArtistRespCase(instance()) }
        bind<FetchChartTopTagCase>() with singleton { FetchChartTopTagRespCase(instance()) }
        bind<FetchChartTopTrackCase>() with singleton { FetchChartTopTrackRespCase(instance()) }
        bind<FetchSimilarArtistCase>() with singleton { FetchSimilarArtistRespCase(instance()) }
        bind<FetchSimilarTrackCase>() with singleton { FetchSimilarTrackRespCase(instance()) }
        bind<FetchTagCase>() with singleton { FetchTagRespCase(instance()) }
        bind<FetchTagTopAlbumCase>() with singleton { FetchTagTopAlbumRespCase(instance()) }
        bind<FetchTagTopArtistCase>() with singleton { FetchTagTopArtistRespCase(instance()) }
        bind<FetchTagTopTrackCase>() with singleton { FetchTagTopTrackRespCase(instance()) }
        bind<FetchTrackCase>() with singleton { FetchTrackRespCase(instance()) }
        //endregion

        //region History
        bind<AddSearchHistCase>() with singleton { AddSearchHistRespCase(instance()) }
        bind<DeleteSearchHistCase>() with singleton { DeleteSearchHistRespCase(instance()) }
        bind<FetchSearchHistsCase>() with singleton { FetchSearchHistsRespCase(instance()) }
        //endregion

        bind<AddLocalMusicCase>() with singleton { AddLocalMusicRespCase(instance()) }
        bind<AddPlaylistsCase>() with singleton { AddPlaylistsRespCase(instance()) }
        bind<UpdatePlaylistsCase>() with singleton { UpdatePlaylistsRespCase(instance()) }
        bind<DeletePlaylistsCase>() with singleton { DeletePlaylistsRespCase(instance()) }
    }
}
