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

import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AlbumWithArtistPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AlbumWithArtistTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPhotoPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistPhotosPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistSimilarPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ArtistsSimilarPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.AttrPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.BioPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.ImagePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.LinkPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.StatsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.StreamPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TagPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TagsPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TopAlbumTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TrackPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TrackTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TrackWithStreamablePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksTypeGenrePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.TracksWithStreamablePMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.lastfm.WikiPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.HotListPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MusicPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.MvPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.SongListPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.SongPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.musicbank.UserPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.others.RankingPMapper
import com.no1.taiwan.stationmusicfm.entities.mappers.others.SearchHistoryPMapper
import com.no1.taiwan.stationmusicfm.internal.di.PreziMapperEntry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

object PresentationMapperModule {
    /**
     * Import this module for each activity entry, they don't be needed in the beginning.
     */
    fun presentationUtilProvider() = Kodein.Module("Presentation Layer Util") {
        /** Mapper Set for [com.no1.taiwan.stationmusicfm.entities.mappers.Mapper] */
        bind() from setBinding<PreziMapperEntry>()

        /** Presentation Layer Mapper */
        val songMapper = SongPMapper(MvPMapper())
        val songListMapper = SongListPMapper(songMapper, UserPMapper())
        bind<PreziMapperEntry>().inSet() with singleton { MusicPMapper::class.java to MusicPMapper(songMapper) }
        bind<PreziMapperEntry>().inSet() with singleton { SongListPMapper::class.java to songListMapper }
        bind<PreziMapperEntry>().inSet() with singleton {
            HotListPMapper::class.java to HotListPMapper(songListMapper)
        }

        val tagMapper = TagPMapper(WikiPMapper())
        val bioMapper = BioPMapper(LinkPMapper())
        val artistMapper = ArtistPMapper(bioMapper, ImagePMapper(), StatsPMapper(), tagMapper)
        val artistSimilarMapper = ArtistSimilarPMapper(bioMapper, ImagePMapper(), StatsPMapper(), tagMapper)
        val trackMapper = TrackPMapper(artistMapper,
                                       AttrPMapper(),
                                       ImagePMapper(),
                                       StreamPMapper(),
                                       tagMapper,
                                       WikiPMapper())
        val trackTypeGenre = TrackTypeGenrePMapper(artistMapper,
                                                   AttrPMapper(),
                                                   ImagePMapper(),
                                                   StreamPMapper(),
                                                   tagMapper,
                                                   trackMapper,
                                                   WikiPMapper())
        val albumMapper = AlbumPMapper(AttrPMapper(), ImagePMapper(), tagMapper, trackMapper, WikiPMapper())
        val trackWithStreamableMapper = TrackWithStreamablePMapper(albumMapper,
                                                                   artistMapper,
                                                                   AttrPMapper(),
                                                                   ImagePMapper(),
                                                                   tagMapper,
                                                                   WikiPMapper())
        bind<PreziMapperEntry>().inSet() with singleton { AlbumPMapper::class.java to albumMapper }
        bind<PreziMapperEntry>().inSet() with singleton { ArtistPMapper::class.java to artistMapper }
        bind<PreziMapperEntry>().inSet() with singleton {
            ArtistsPMapper::class.java to ArtistsPMapper(artistMapper, AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            ArtistsSimilarPMapper::class.java to ArtistsSimilarPMapper(artistSimilarMapper, AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton { TagPMapper::class.java to tagMapper }
        bind<PreziMapperEntry>().inSet() with singleton {
            TagsPMapper::class.java to TagsPMapper(tagMapper, AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            TopAlbumPMapper::class.java to TopAlbumPMapper(AlbumWithArtistPMapper(artistMapper,
                                                                                  AttrPMapper(),
                                                                                  ImagePMapper(),
                                                                                  tagMapper,
                                                                                  trackMapper,
                                                                                  WikiPMapper()), AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            TopAlbumTypeGenrePMapper::class.java to TopAlbumTypeGenrePMapper(AlbumWithArtistTypeGenrePMapper(
                artistMapper,
                AttrPMapper(),
                ImagePMapper(),
                tagMapper,
                trackMapper,
                WikiPMapper()), AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton { TrackPMapper::class.java to trackMapper }
        bind<PreziMapperEntry>().inSet() with singleton {
            TracksTypeGenrePMapper::class.java to TracksTypeGenrePMapper(trackTypeGenre, AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            TracksPMapper::class.java to TracksPMapper(trackMapper, AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton {
            TracksWithStreamablePMapper::class.java to TracksWithStreamablePMapper(trackWithStreamableMapper,
                                                                                   AttrPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton { RankingPMapper::class.java to RankingPMapper() }
        bind<PreziMapperEntry>().inSet() with singleton {
            ArtistPhotosPMapper::class.java to ArtistPhotosPMapper(ArtistPhotoPMapper())
        }
        bind<PreziMapperEntry>().inSet() with singleton { SearchHistoryPMapper::class.java to SearchHistoryPMapper() }
    }
}
