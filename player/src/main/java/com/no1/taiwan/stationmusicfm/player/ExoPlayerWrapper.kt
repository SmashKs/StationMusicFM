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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
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
import com.no1.taiwan.stationmusicfm.player.playlist.PlaylistQueue
import com.no1.taiwan.stationmusicfm.player.utils.PausableTimer

class ExoPlayerWrapper(private val context: Context) : MusicPlayer {
    companion object {
        private const val TAG = "ExoPlayerWrapper"
        private const val NAME = "LocalExoPlayer"
    }

    override var isPlaying = false
    override var curPlayingUri = ""
    override val playingMode get() = playlist.mode
    private val exoPlayer by lazy {
        Log.i(TAG, "init ExoPlayer")
        ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector()).apply {
            addListener(LocalPlayerEventListener(this@ExoPlayerWrapper, this))
        }
    }
    private val playlist: Playlist<String> by lazy { PlaylistQueue() }
    private lateinit var timer: PausableTimer
    private var playerState: MusicPlayerState = Standby
        set(value) {
            if (value == field) return
            field = value
            // Call the callback function.
            listener?.onPlayerStateChanged(value)
        }
    private var listener: ExoPlayerEventListener.PlayerEventListener? = null

    /**
     * Start playing a music.
     * This function will play the music which is specified with an URI.
     * If the [uri] is blank string, the function is also about play the music, but when
     * the music is playing, executing this function will pause the music.
     * If the music is pausing, executing this function will resume the music.
     * If playing is failed, the function returns false.
     */
    override fun play(uri: String): Boolean {
        if (uri.isBlank()) {
            if (isPlaying) {
                timer.pause()
                playerState = Pause
            }
            else {
                timer.resume()
                playerState = Play
            }
            exoPlayer.playWhenReady = !isPlaying
        }
        else {
            if (curPlayingUri == uri) return false
            // Prepare the player with the source.
            exoPlayer.apply {
                prepare(buildMediaSource(uri))
                playWhenReady = true
            }
            playerState = Play
            curPlayingUri = uri
        }

        return true
    }

    override fun play(index: Int): Boolean {
        val res = playlist.setStartIndex(index)
        if (!res) return res
        play(requireNotNull(playlist.current))
        return res
    }

    /**
     * Stop playing the music.
     */
    override fun stop() {
        if (playerState is Standby) return
        exoPlayer.playWhenReady = false
        exoPlayer.stop()
        timer.stop()
        playerState = Standby
    }

    /**
     * Pause a music. If no music is played, nothing to do.
     */
    override fun pause() {
        if (playerState is Pause) return
        exoPlayer.playWhenReady = false
        timer.pause()
        playerState = Pause
    }

    /**
     * Resume the playing of the music.
     */
    override fun resume() {
        if (playerState is Play) return
        exoPlayer.playWhenReady = true
        timer.resume()
        playerState = Play
    }

    override fun next() {
        val uri = playlist.goNext() ?: return
        play(uri)
    }

    override fun previous() {
        val uri = playlist.goPrevious() ?: return
        play(uri)
    }

    override fun clearPlaylist() = playlist.clear()

    override fun addToPlaylist(list: List<String>): Boolean {
        return playlist.enqueue(list)
    }

    override fun replacePlaylist(list: List<String>) {
        clearPlaylist()
        addToPlaylist(list)
    }

    override fun setShuffle() {
        playlist.mode = Playlist.Mode.Shuffle
    }

    override fun setRepeat(isRepeat: Boolean) {
        exoPlayer.repeatMode = if (isRepeat) {
            playlist.mode = Playlist.Mode.RepeatOne
            Player.REPEAT_MODE_ONE
        }
        else {
            playlist.mode = Playlist.Mode.Default
            Player.REPEAT_MODE_OFF
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
        // TODO(jieyi): 2019-03-21 https://medium.com/google-exoplayer/dynamic-playlists-with-exoplayer-6f53e54a56c0
        // This is the MediaSource representing the media to be played.
        return ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(uri)
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

                musicPlayer.timer =
                    PausableTimer(exoPlayer.duration.minus(exoPlayer.currentPosition),
                                  millis.toLong())
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
