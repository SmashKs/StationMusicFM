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

package com.no1.taiwan.stationmusicfm.internal.di

import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.AlbumOfGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreArtistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExplorePhotoViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotAlbumViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.SimilarArtistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.TrackOfGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.ChartViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.RankTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.TopperViewHolder
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_KEYWORD
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.ViewHolderEntry
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.utils.MusicDiffUtil
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.setBinding
import org.kodein.di.generic.singleton

/**
 * To provide the necessary objects into the recycler view.
 */
object RecyclerViewModule {
    fun recyclerViewProvider() = Module("Recycler View") {
        import(adapterProvider())
        import(diffUtilProvider())
        import(mappingProvider())
        import(viewHolderProvider())
    }

    private fun mappingProvider() = Module("View Holder Mapping") {
        /** ViewModel Set for [MultiTypeFactory] */
        bind() from setBinding<ViewHolderEntry>()
    }

    private fun adapterProvider() = Module("Recycler View Adapter") {
        bind<MultiTypeFactory>() with singleton {
            MultiTypeFactory(instance())
        }
        bind<MusicAdapter>() with provider {
            MultiTypeAdapter(mutableListOf(), instance())
        }
    }

    private fun diffUtilProvider() = Module("Recycler View DiffUtil") {
        bind<MusicDiffUtil>(UTIL_DIFF_KEYWORD) with instance(MusicDiffUtil())
    }

    private fun viewHolderProvider() = Module("Viewholder Module") {
        // *** ViewHolder
        bind<ViewHolderEntry>().inSet() with provider {
            RankingIdEntity::class.hashCode() to (R.layout.item_rank_top_header to ::TopperViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            RankingIdForChartItem::class.hashCode() to (R.layout.item_rank_chart to ::ChartViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            CommonMusicEntity.SongEntity::class.hashCode() to (R.layout.item_rank_detail to ::RankTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            ArtistInfoEntity.ArtistEntity::class.hashCode() to (R.layout.item_explore_artist to ::ExploreArtistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TagInfoEntity.TagEntity::class.hashCode() to (R.layout.item_explore_genre to ::ExploreGenreViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackInfoEntity.TrackEntity::class.hashCode() to (R.layout.item_explore_track to ::ExploreTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            ArtistInfoEntity.PhotoEntity::class.hashCode() to (R.layout.item_explore_photo to ::ExplorePhotoViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            AlbumInfoEntity.AlbumWithArtistEntity::class.hashCode() to (R.layout.item_hot_album to ::HotAlbumViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackInfoEntity.TrackWithStreamableEntity::class.hashCode() to (R.layout.item_hot_track to ::HotTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            ArtistInfoEntity.ArtistSimilarEntity::class.hashCode() to (R.layout.item_similar_artist to ::SimilarArtistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            AlbumInfoEntity.AlbumWithArtistTypeGenreEntity::class.hashCode() to (R.layout.item_genre_of_album to ::AlbumOfGenreViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackInfoEntity.TrackTypeGenreEntity::class.hashCode() to (R.layout.item_genre_of_track to ::TrackOfGenreViewHolder)
        }
    }
}
