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

package com.no1.taiwan.stationmusicfm.data.repositories

import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.AlbumDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistPhotosDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.ArtistsDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TagDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TagsDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TopAlbumDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TrackDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TracksDMapper
import com.no1.taiwan.stationmusicfm.data.data.mappers.lastfm.TracksWithStreamableDMapper
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.data.delegates.DataMapperDigger
import com.no1.taiwan.stationmusicfm.domain.parameters.Parameterable
import com.no1.taiwan.stationmusicfm.domain.repositories.LastFmRepository
import com.no1.taiwan.stationmusicfm.ext.exceptions.EmptyException
import kotlinx.coroutines.async

/**
 * The data repository for being responsible for selecting an appropriate data store to access
 * the data.
 * Also we need to do [async] & [await] one time for getting the data then transform and wrap to Domain layer.
 *
 * @property local from database/file/memory data store.
 * @property remote from remote sever/firebase.
 * @property diggerDelegate keeping all of the data mapper here.
 */
class LastFmDataRepository constructor(
    private val local: DataStore,
    private val remote: DataStore,
    diggerDelegate: DataMapperDigger
) : LastFmRepository, DataMapperDigger by diggerDelegate {
    private val albumMapper by lazy { digMapper(AlbumDMapper::class) }
    private val artistMapper by lazy { digMapper(ArtistDMapper::class) }
    private val artistsMapper by lazy { digMapper(ArtistsDMapper::class) }
    private val tagMapper by lazy { digMapper(TagDMapper::class) }
    private val tagsMapper by lazy { digMapper(TagsDMapper::class) }
    private val topAlbumMapper by lazy { digMapper(TopAlbumDMapper::class) }
    private val trackMapper by lazy { digMapper(TrackDMapper::class) }
    private val tracksMapper by lazy { digMapper(TracksDMapper::class) }
    private val tracksWithStreamableMapper by lazy { digMapper(TracksWithStreamableDMapper::class) }
    private val artistPhotosMapper by lazy { digMapper(ArtistPhotosDMapper::class) }

    override suspend fun fetchAlbum(parameters: Parameterable) =
        remote.getAlbumInfo(parameters).album?.run(albumMapper::toModelFrom) ?: throw EmptyException()

    override suspend fun fetchArtist(parameters: Parameterable) =
        remote.getArtistInfo(parameters).artist?.run(artistMapper::toModelFrom) ?: throw EmptyException()

    override suspend fun fetchArtistTopAlbum(parameters: Parameterable) =
        remote.getArtistTopAlbum(parameters).topAlbums.run(topAlbumMapper::toModelFrom)

    override suspend fun fetchArtistTopTrack(parameters: Parameterable) =
        remote.getArtistTopTrack(parameters).topTracks.run(tracksWithStreamableMapper::toModelFrom)

    override suspend fun fetchSimilarArtistInfo(parameters: Parameterable) =
        remote.getSimilarArtistInfo(parameters).similarArtist.run(artistsMapper::toModelFrom)

    override suspend fun fetchArtistPhotoInfo(parameters: Parameterable) =
        remote.getArtistPhotosInfo(parameters).run(artistPhotosMapper::toModelFrom)

    override suspend fun fetchTrack(parameters: Parameterable) =
        remote.getTrackInfo(parameters).track?.run(trackMapper::toModelFrom) ?: throw EmptyException()

    override suspend fun fetchSimilarTrackInfo(parameters: Parameterable) =
        remote.getSimilarTrackInfo(parameters).similarTracks.run(tracksMapper::toModelFrom)

    override suspend fun fetchChartTopTrack(parameters: Parameterable) =
        remote.getChartTopTrack(parameters).track.run(tracksMapper::toModelFrom)

    override suspend fun fetchChartTopArtist(parameters: Parameterable) =
        remote.getChartTopArtist(parameters).artists.run(artistsMapper::toModelFrom)

    override suspend fun fetchChartTopTag(parameters: Parameterable) =
        remote.getChartTopTag(parameters).tag.run(tagsMapper::toModelFrom)

    override suspend fun fetchTag(parameters: Parameterable) =
        remote.getTagInfo(parameters).tag.run(tagMapper::toModelFrom)

    override suspend fun fetchTagTopAlbum(parameters: Parameterable) =
        remote.getTagTopAlbum(parameters).albums.run(topAlbumMapper::toModelFrom)

    override suspend fun fetchTagTopArtist(parameters: Parameterable) =
        remote.getTagTopArtist(parameters).topArtists.run(artistsMapper::toModelFrom)

    override suspend fun fetchTagTopTrack(parameters: Parameterable) =
        remote.getTagTopTrack(parameters).track.run(tracksMapper::toModelFrom)
}
