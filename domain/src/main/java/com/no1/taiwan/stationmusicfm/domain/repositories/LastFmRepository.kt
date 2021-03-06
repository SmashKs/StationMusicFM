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

package com.no1.taiwan.stationmusicfm.domain.repositories

import com.no1.taiwan.stationmusicfm.domain.models.lastfm.AlbumInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.ArtistInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.CommonLastFmModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TagInfoModel
import com.no1.taiwan.stationmusicfm.domain.models.lastfm.TrackInfoModel
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable

/**
 * This interface will be the similar to [com.no1.taiwan.stationmusicfm.data.datastores.DataStore].
 * Using prefix name (fetch), (add), (update), (delete), (keep)
 */
interface LastFmRepository {
    //region AlbumModel
    suspend fun fetchAlbum(parameters: Parameterable): AlbumInfoModel.AlbumModel
    //endregion

    //region ArtistModel
    suspend fun fetchArtist(parameters: Parameterable): ArtistInfoModel.ArtistModel

    suspend fun fetchArtistTopAlbum(parameters: Parameterable): CommonLastFmModel.TopAlbumsModel

    suspend fun fetchArtistTopTrack(parameters: Parameterable): TrackInfoModel.TracksWithStreamableModel

    suspend fun fetchSimilarArtistInfo(parameters: Parameterable): ArtistInfoModel.ArtistsModel

    suspend fun fetchArtistPhotoInfo(parameters: Parameterable): ArtistInfoModel.ArtistPhotosModel
    //endregion

    //region TrackModel
    suspend fun fetchTrack(parameters: Parameterable): TrackInfoModel.TrackModel

    suspend fun fetchSimilarTrackInfo(parameters: Parameterable): TrackInfoModel.TracksModel
    //endregion

    //region Chart
    suspend fun fetchChartTopTrack(parameters: Parameterable): TrackInfoModel.TracksModel

    suspend fun fetchChartTopArtist(parameters: Parameterable): ArtistInfoModel.ArtistsModel

    suspend fun fetchChartTopTag(parameters: Parameterable): CommonLastFmModel.TagsModel
    //endregion

    //region TagModel
    suspend fun fetchTag(parameters: Parameterable): TagInfoModel.TagModel

    suspend fun fetchTagTopAlbum(parameters: Parameterable): CommonLastFmModel.TopAlbumsModel

    suspend fun fetchTagTopArtist(parameters: Parameterable): ArtistInfoModel.ArtistsModel

    suspend fun fetchTagTopTrack(parameters: Parameterable): TrackInfoModel.TracksModel
    //endregion
}
