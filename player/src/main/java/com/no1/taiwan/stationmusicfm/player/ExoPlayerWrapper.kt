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

package com.no1.taiwan.stationmusicfm.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.no1.taiwan.stationmusicfm.player.MusicPlayerState.Pause
import com.no1.taiwan.stationmusicfm.player.MusicPlayerState.Play
import com.no1.taiwan.stationmusicfm.player.MusicPlayerState.Standby
import com.no1.taiwan.stationmusicfm.player.helpers.DownloadHandler
import com.no1.taiwan.stationmusicfm.player.playlist.Playlist
import com.no1.taiwan.stationmusicfm.player.utils.PausableTimer

class ExoPlayerWrapper(private val context: Context) : MusicPlayer {
    companion object {
        private const val TAG = "ExoPlayerWrapper"
        private const val NAME = "LocalExoPlayer"
    }

    override var isPlaying = false
    override val curPlayingUri get() = (exoPlayer.currentTag as? String).orEmpty()
    override var playingMode: Playlist.Mode = Playlist.Mode.Default
    private val exoPlayer by lazy {
        Log.i(TAG, "init ExoPlayer")
        ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector()).apply {
            prepare(playlist)
            addListener(LocalPlayerEventListener(this@ExoPlayerWrapper, this))
        }
    }
    private lateinit var timer: PausableTimer
    private var playerState: MusicPlayerState = Standby
        set(value) {
            if (value == field) return
            field = value
//            when (value) {
//                Standby -> timer.stop()
//                Play -> timer.resume()
//                Pause -> timer.pause()
//            }
            // Call the callback function.
            listener?.onPlayerStateChanged(value)
        }
    private val playlist by lazy { ConcatenatingMediaSource() }
    private val duplicatedList by lazy { mutableListOf<String>() }
    private val isTheSame: Boolean
        get() {
            if (playlist.size != duplicatedList.size)
                return false
            duplicatedList.forEachIndexed { index, uri ->
                if ((playlist.getMediaSource(index).tag as String) != uri)
                    return false
            }
            return true
        }
    private var listener: ExoPlayerEventListener.PlayerEventListener? = null
    private var individualPlay = false

    init {
        exoPlayer
    }

    /**
     * Start playing a music.
     * This function will play the music which is specified with an URI.
     * If the [uri] is blank string, the function is also about play the music, but when
     * the music is playing, executing this function will pause the music.
     * If the music is pausing, executing this function will resume the music.
     * If playing is failed, the function returns false.
     */
    override fun play(uri: String): Boolean {
        // Play the media from the build-in [exoPlayer]'s playlist.
        if (uri.isBlank()) {
            playerState = if (isPlaying) Pause else Play
            isPlaying = !isPlaying
            exoPlayer.playWhenReady = isPlaying
        }
        // Play a single individual uri.
        else {
            if (curPlayingUri == uri) return false
            // Prepare the player with the source.
            exoPlayer.apply {
                prepare(buildMediaSource(uri))
                playWhenReady = true
            }
            individualPlay = true
            playerState = Play
        }

        return true
    }

    override fun play(index: Int): Boolean {
        // If play the difference track, it should be reset.
        if (duplicatedList[index] != curPlayingUri) {
            if (!isTheSame) {
                playlist.clear()
                playlist.addMediaSources(duplicatedList.map(::buildMediaSource))
            }
            // According to index to play the music from the playlist.
            exoPlayer.apply {
                resetPlayerState()
                seekTo(index, C.TIME_UNSET)
            }
        }
        return play()
    }

    /**
     * Stop playing the music.
     */
    override fun stop() {
        if (playerState is Standby) return
        exoPlayer.playWhenReady = false
        exoPlayer.stop()
        resetPlayerState()
    }

    /**
     * Pause a music. If no music is played, nothing to do.
     */
    override fun pause() {
        if (playerState is Pause) return
        exoPlayer.playWhenReady = false
        playerState = Pause
    }

    /**
     * Resume the playing of the music.
     */
    override fun resume() {
        if (playerState is Play) return
        exoPlayer.playWhenReady = true
        playerState = Play
    }

    /**
     * Play the next track from the playlist.
     */
    override fun next() {
        exoPlayer.apply {
            if (hasNext())
                next()
        }
    }

    /**
     * Play the previous track from the playlist.
     */
    override fun previous() {
        exoPlayer.apply {
            if (hasPrevious())
                previous()
        }
    }

    /**
     * Clear all tracks from the playlist.
     */
    override fun clearPlaylist() {
        duplicatedList.clear()
    }

    /**
     * Add a new track [list] into the playlist.
     *
     * @param list
     * @return
     */
    override fun addToPlaylist(list: List<String>): Boolean {
        if (individualPlay) {
            exoPlayer.prepare(playlist)
            individualPlay = false
        }
        // Set backup list temporally because it might not be played.
        duplicatedList.addAll(list)
        return true
    }

    /**
     * Clear original list playlist has then add a new track [list] into the playlist.
     *
     * @param list
     */
    override fun replacePlaylist(list: List<String>) {
        clearPlaylist()
        addToPlaylist(list)
    }

    override fun setPlayMode(mode: Playlist.Mode) {
        playingMode = mode
        when (mode) {
            Playlist.Mode.Default -> {
                exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
                exoPlayer.shuffleModeEnabled = false
            }
            Playlist.Mode.RepeatOne -> {
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                exoPlayer.shuffleModeEnabled = false
            }
            Playlist.Mode.RepeatAll -> {
                exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
                exoPlayer.shuffleModeEnabled = false
            }
            Playlist.Mode.Shuffle -> exoPlayer.shuffleModeEnabled = true
        }
    }

    override fun seekTo(sec: Int) {
        exoPlayer.seekTo(sec.times(1000).toLong())
    }

    override fun getPlayerState() = playerState

    override fun writeToFile(url: String, filePath: String?): Boolean {
        val downloadHandler = DownloadHandler(url, filePath, listener)
        downloadHandler.start()
        return true
    }

    override fun setEventListener(listener: ExoPlayerEventListener.PlayerEventListener) {
        this.listener = listener
    }

    private fun buildMediaSource(url: String): MediaSource {
        val uri = Uri.parse(url)
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, NAME), DefaultBandwidthMeter())
        // This is the MediaSource representing the media to be played.
        return ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .setTag(url)  // Keep the uri to tag.
            .createMediaSource(uri)
    }

    private fun resetPlayerState() {
        isPlaying = false
        playerState = Standby
    }

    private open class LocalPlayerEventListener(
        private val musicPlayer: ExoPlayerWrapper,
        private val exoPlayer: ExoPlayer
    ) : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            musicPlayer.isPlaying = playWhenReady
            if (playbackState == STATE_ENDED) {
                musicPlayer.playerState = Standby
                musicPlayer.next()
            }
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            musicPlayer.listener?.onBufferPercentage(exoPlayer.bufferedPercentage)
        }

        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            val millis = 1000
            val threshold = 1_000_000  // Avoiding the bigger timeline comes, cause the running time is incorrect.

            if (exoPlayer.duration in 1..threshold) {
                musicPlayer.listener?.onDurationChanged(exoPlayer.duration.div(millis).toInt())
                musicPlayer.timer = PausableTimer(exoPlayer.duration.minus(exoPlayer.currentPosition), millis.toLong())
                musicPlayer.timer.onTick = { millisUntilFinished ->
                    val time = (exoPlayer.duration - millisUntilFinished).div(millis).toInt()
                    musicPlayer.listener?.onCurrentTime(time)
                }
                musicPlayer.timer.onFinish = {
                    musicPlayer.listener?.onCurrentTime(0)
                }
                musicPlayer.timer.start()
            }
        }
    }
}
