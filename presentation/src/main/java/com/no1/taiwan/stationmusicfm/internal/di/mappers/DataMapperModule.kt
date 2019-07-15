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

package com.no1.taiwan.stationmusicfm.internal.di.mappers

import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.AlbumDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.AlbumWithArtistDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistPhotoDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistPhotosDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistsDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.AttrDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.BioDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ImageDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.LinkDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.StatsDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.StreamDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TagDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TagsDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TopAlbumDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TrackDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TrackWithStreamableDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TracksDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TracksWithStreamableDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.WikiDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.BriefRankDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.HotListDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.MusicDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.MvDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.SongDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.SongListDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.musicbank.UserDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.others.RankingDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.others.SearchHistoryDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.playlist.LocalMusicDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.playlist.PlaylistInfoDMapper
import com.no1.taiwan.stationmusicfm.internal.di.DataMapperEntry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

object DataMapperModule {
    /**
     * Import this module in the [com.no1.taiwan.stationmusicfm.MusicApp] because data layer needs this.
     */
    fun dataUtilProvider() = Kodein.Module("Data Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.entities.mappers.Mapper] */
        bind() from setBinding<DataMapperEntry>()

        /** RankInfoData Layer Mapper */
        val songMapper = SongDMapper(MvDMapper())
        val songListMapper = SongListDMapper(songMapper, UserDMapper())
        bind<DataMapperEntry>().inSet() with singleton {
            MusicDMapper::class.java to MusicDMapper(songMapper)
        }
        bind<DataMapperEntry>().inSet() with singleton { SongListDMapper::class.java to songListMapper }
        bind<DataMapperEntry>().inSet() with singleton {
            HotListDMapper::class.java to HotListDMapper(songListMapper)
        }

        val tagMapper = TagDMapper(WikiDMapper())
        val bioMapper = BioDMapper(LinkDMapper())
        val artistMapper = ArtistDMapper(bioMapper, ImageDMapper(), StatsDMapper(), tagMapper)
        val trackMapper = TrackDMapper(artistMapper,
                                       AttrDMapper(),
                                       ImageDMapper(),
                                       StreamDMapper(),
                                       tagMapper,
                                       WikiDMapper())
        val albumMapper =
            AlbumDMapper(AttrDMapper(), ImageDMapper(), tagMapper, trackMapper, WikiDMapper())
        val trackWithStreamableMapper = TrackWithStreamableDMapper(albumMapper,
                                                                   artistMapper,
                                                                   AttrDMapper(),
                                                                   ImageDMapper(),
                                                                   tagMapper,
                                                                   WikiDMapper())
        bind<DataMapperEntry>().inSet() with singleton { AlbumDMapper::class.java to albumMapper }
        bind<DataMapperEntry>().inSet() with singleton { ArtistDMapper::class.java to artistMapper }
        bind<DataMapperEntry>().inSet() with singleton {
            ArtistsDMapper::class.java to ArtistsDMapper(artistMapper, AttrDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton { TagDMapper::class.java to tagMapper }
        bind<DataMapperEntry>().inSet() with singleton {
            TagsDMapper::class.java to TagsDMapper(tagMapper, AttrDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton {
            TopAlbumDMapper::class.java to TopAlbumDMapper(AlbumWithArtistDMapper(artistMapper,
                                                                                  AttrDMapper(),
                                                                                  ImageDMapper(),
                                                                                  tagMapper,
                                                                                  trackMapper,
                                                                                  WikiDMapper()),
                                                           AttrDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton { TrackDMapper::class.java to trackMapper }
        bind<DataMapperEntry>().inSet() with singleton {
            TracksDMapper::class.java to TracksDMapper(trackMapper, AttrDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton {
            TracksWithStreamableDMapper::class.java to TracksWithStreamableDMapper(
                trackWithStreamableMapper,
                AttrDMapper())
        }

        bind<DataMapperEntry>().inSet() with singleton { RankingDMapper::class.java to RankingDMapper() }
        bind<DataMapperEntry>().inSet() with singleton {
            ArtistPhotosDMapper::class.java to ArtistPhotosDMapper(ArtistPhotoDMapper())
        }
        bind<DataMapperEntry>().inSet() with singleton { SearchHistoryDMapper::class.java to SearchHistoryDMapper() }
        bind<DataMapperEntry>().inSet() with singleton { LocalMusicDMapper::class.java to LocalMusicDMapper() }
        bind<DataMapperEntry>().inSet() with singleton { PlaylistInfoDMapper::class.java to PlaylistInfoDMapper() }
        bind<DataMapperEntry>().inSet() with singleton { BriefRankDMapper::class.java to BriefRankDMapper() }
    }
}
