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

import androidx.recyclerview.widget.RecyclerView
import com.no1.taiwan.stationmusicfm.MusicApp
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.AlbumWithArtistEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.AlbumInfoEntity.AlbumWithArtistTypeGenreEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.ArtistSimilarEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.ArtistInfoEntity.PhotoEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TagInfoEntity.TagEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackTypeGenreEntity
import com.no1.taiwan.stationmusicfm.entities.lastfm.TrackInfoEntity.TrackWithStreamableEntity
import com.no1.taiwan.stationmusicfm.entities.musicbank.CommonMusicEntity.SongEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdEntity
import com.no1.taiwan.stationmusicfm.entities.others.RankingIdForChartItem
import com.no1.taiwan.stationmusicfm.entities.others.SearchHistoryEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.CreatePlaylistEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.LocalMusicEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistBottomSheetEntity
import com.no1.taiwan.stationmusicfm.entities.playlist.PlaylistInfoEntity
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.AlbumOfGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreArtistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExplorePhotoViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.ExploreTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotAlbumViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.HotTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.SimilarArtistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.explore.viewholders.TrackOfGenreViewHolder
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.CreatePlaylistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.LocalMusicViewHolder
import com.no1.taiwan.stationmusicfm.features.main.mymusic.viewholders.PlaylistViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.ChartViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.RankTrackViewHolder
import com.no1.taiwan.stationmusicfm.features.main.rank.viewholders.TopperViewHolder
import com.no1.taiwan.stationmusicfm.features.main.search.viewholders.SearchHistoryViewHolder
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_HISTORY
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_RANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ADAPTER_TRACK_OF_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_ACTION_BAR_BLANK
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_MUSIC_OF_PLAYLIST_SEPARATOR
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.ITEM_DECORATION_SEPARATOR
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_KEYWORD
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_PLAYLIST
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.UTIL_DIFF_RANK
import com.no1.taiwan.stationmusicfm.kits.bottomsheet.viewholders.BottomPlaylistViewHolder
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.HistoryAdapter
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.NotifiableAdapter
import com.no1.taiwan.stationmusicfm.kits.recyclerview.adapters.TrackOfPlaylistNotifiableAdapter
import com.no1.taiwan.stationmusicfm.kits.recyclerview.diffutils.PlaylistDiffUtil
import com.no1.taiwan.stationmusicfm.kits.recyclerview.diffutils.RankDiffUtil
import com.no1.taiwan.stationmusicfm.kits.recyclerview.scrolllisteners.LoadMoreScrollListener
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MultiTypeFactory
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicMultiDiffUtil
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.ViewHolderEntry
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.adapters.MultiTypeAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.decorations.ActionBarBlankDecoration
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.decorations.SeparateLineDecoration
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.decorations.TrackOfPlaylistSeparateLineDecoration
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.utils.MusicDefaultDiffUtil
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
        import(decorationProvider())
        import(scrollListenerProvider())
        import(mappingProvider())
        import(viewHolderProvider())
    }

    private fun mappingProvider() = Module("View Holder Mapping") {
        /** ViewModel Set for [MultiTypeFactory] */
        bind() from setBinding<ViewHolderEntry>()
    }

    private fun adapterProvider() = Module("Recycler View Adapter") {
        bind<MultiTypeFactory>() with singleton { MultiTypeFactory(instance()) }
        // *** Adapters
        bind<MusicAdapter>() with provider { MultiTypeAdapter(instance()) }
        bind<MusicAdapter>(ADAPTER_RANK) with provider {
            MultiTypeAdapter(instance(),
                             instance(UTIL_DIFF_RANK))
        }
        bind<MusicAdapter>(ADAPTER_PLAYLIST) with provider {
            MultiTypeAdapter(instance(), instance(UTIL_DIFF_PLAYLIST))
        }
        bind<NotifiableAdapter>(ADAPTER_TRACK) with provider { NotifiableAdapter(instance()) }
        bind<HistoryAdapter>(ADAPTER_HISTORY) with provider {
            HistoryAdapter(instance(), instance(UTIL_DIFF_KEYWORD))
        }
        bind<TrackOfPlaylistNotifiableAdapter>(ADAPTER_TRACK_OF_PLAYLIST) with provider {
            TrackOfPlaylistNotifiableAdapter(instance())
        }
    }

    private fun diffUtilProvider() = Module("Recycler View DiffUtil") {
        bind<MusicMultiDiffUtil>(UTIL_DIFF_KEYWORD) with instance(MusicDefaultDiffUtil())
        bind<MusicMultiDiffUtil>(UTIL_DIFF_RANK) with instance(RankDiffUtil())
        bind<MusicMultiDiffUtil>(UTIL_DIFF_PLAYLIST) with instance(PlaylistDiffUtil())
    }

    private fun decorationProvider() = Module("Recycler View Item Decoration") {
        bind<RecyclerView.ItemDecoration>(ITEM_DECORATION_ACTION_BAR_BLANK) with provider { ActionBarBlankDecoration() }
        bind<RecyclerView.ItemDecoration>(ITEM_DECORATION_SEPARATOR) with provider {
            SeparateLineDecoration(MusicApp.appContext)
        }
        bind<RecyclerView.ItemDecoration>(ITEM_DECORATION_MUSIC_OF_PLAYLIST_SEPARATOR) with provider {
            TrackOfPlaylistSeparateLineDecoration(MusicApp.appContext)
        }
    }

    private fun scrollListenerProvider() = Module("Recycler View Scroll Listener") {
        bind<LoadMoreScrollListener>() with provider { LoadMoreScrollListener() }
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
            SongEntity::class.hashCode() to (R.layout.item_rank_detail to ::RankTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            ArtistEntity::class.hashCode() to (R.layout.item_explore_artist to ::ExploreArtistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TagEntity::class.hashCode() to (R.layout.item_explore_genre to ::ExploreGenreViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackEntity::class.hashCode() to (R.layout.item_explore_track to ::ExploreTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            PhotoEntity::class.hashCode() to (R.layout.item_explore_photo to ::ExplorePhotoViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            AlbumWithArtistEntity::class.hashCode() to (R.layout.item_hot_album to ::HotAlbumViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackWithStreamableEntity::class.hashCode() to (R.layout.item_hot_track to ::HotTrackViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            ArtistSimilarEntity::class.hashCode() to (R.layout.item_similar_artist to ::SimilarArtistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            AlbumWithArtistTypeGenreEntity::class.hashCode() to (R.layout.item_genre_of_album to ::AlbumOfGenreViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            TrackTypeGenreEntity::class.hashCode() to (R.layout.item_genre_of_track to ::TrackOfGenreViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            SearchHistoryEntity::class.hashCode() to (R.layout.item_search_history to ::SearchHistoryViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            PlaylistInfoEntity::class.hashCode() to (R.layout.item_playlist to ::PlaylistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            CreatePlaylistEntity::class.hashCode() to (R.layout.item_new_playlist to ::CreatePlaylistViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            LocalMusicEntity::class.hashCode() to (R.layout.item_playlist_music to ::LocalMusicViewHolder)
        }
        bind<ViewHolderEntry>().inSet() with provider {
            PlaylistBottomSheetEntity::class.hashCode() to (R.layout.item_playlist_on_bottom_sheet to ::BottomPlaylistViewHolder)
        }
    }
}
