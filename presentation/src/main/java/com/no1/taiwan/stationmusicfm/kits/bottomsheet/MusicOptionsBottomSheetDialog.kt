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

package com.no1.taiwan.stationmusicfm.kits.bottomsheet

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.isNotNull
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.entities.MusicEncoder
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.mappers.playlist.PlaylistInfoToBottomEntityPMapper
import com.no1.taiwan.stationmusicfm.internal.di.Dispatcher
import com.no1.taiwan.stationmusicfm.kits.dialogs.AlbumInfoDialog
import com.no1.taiwan.stationmusicfm.ktx.view.find
import com.no1.taiwan.stationmusicfm.player.MusicPlayer
import com.no1.taiwan.stationmusicfm.utils.file.FilePathFactory
import com.no1.taiwan.stationmusicfm.widget.components.toast.toastX
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MusicOptionsBottomSheetDialog(
    activity: Activity,
    lifecycleOwner: LifecycleOwner,
    @StyleRes theme: Int
) : BottomSheetDialog(activity, theme), KodeinAware, LifecycleObserver {
    /** A Kodein Aware class must be within reach of a [Kodein] object. */
    override val kodein = Dispatcher.importIntoBottomSheet(activity.applicationContext)
    private val mapperPool: PreziMapperPool by instance()
    private val playlistMapper by lazy {
        cast<PlaylistInfoToBottomEntityPMapper>(mapperPool[PlaylistInfoToBottomEntityPMapper::class.java])
    }
    private val player: MusicPlayer by instance()
    private val playlistBottomSheet by lazy {
        BottomSheetFactory.createAddPlaylist(activity)
    }
    private val infoDialog by lazy {
        TODO("Not finished yet! sorry")
        AlbumInfoDialog.createDialog(activity as AppCompatActivity) { view, dialog ->
        }
    }
    private val act by WeakRef(activity)
    var songEntity by WeakRef<MusicEncoder>()
    var onDownloaded: ((localMusicPath: String) -> Unit)? = null
    var onDeleted: (() -> Unit)? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStart() {
        super.onStart()
        find<View>(R.id.ftv_download).setOnClickListener {
            songEntity
                // The track has downloaded, just return and dismiss the bottom sheet view.
                ?.takeIf { FilePathFactory.getMusicPath(it.encodeByName()).isNotNull() }
                ?.let {
                    context.toastX("You have downloaded it already.")
                    dismiss()
                    return@setOnClickListener
                }
            songEntity?.let {
                // TODO(jieyi): 2019-04-10 The first time will ask the permission, but no action when a use press ok.
                requireStoragePermission {
                    val path = FilePathFactory.obtainMusicPath(it.encodeByName())
                    // Save into internal storage.
                    player.writeToFile(it.getMusicUri(), path)
                    onDownloaded?.invoke(path)
                }
            }
            dismiss()
        }
        find<View>(R.id.ftv_to_playlist).setOnClickListener {
            dismiss()
            // Open the playlist for adding.
            playlistBottomSheet.show()
        }
        find<View>(R.id.ftv_music_info).setOnClickListener {
            infoDialog?.show()
            dismiss()
        }
        find<View>(R.id.ftv_music_delete).setOnClickListener {
            onDeleted?.invoke()
            dismiss()
        }
    }

    /**
     * Dismiss playlist dialog function.
     */
    fun dismissPlaylist() {
        playlistBottomSheet.takeIf(Dialog::isShowing)?.let(PlaylistBottomSheetDialog::dismiss)
    }

    /**
     * When activity's lifecycle [Lifecycle.Event.ON_DESTROY] happens, the events need to release.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun releaseObjects() {
        if (onDownloaded.isNotNull()) onDownloaded = null
        if (onDeleted.isNotNull()) onDeleted = null
    }

    /**
     * Require the store permission from the activity.
     */
    private fun requireStoragePermission(grantedBlock: (() -> Unit)? = null) {
        if (ActivityCompat.checkSelfPermission(context,
                                               WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            grantedBlock?.invoke()
        }
        else {
            act?.let {
                ActivityCompat.requestPermissions(it, arrayOf(WRITE_EXTERNAL_STORAGE), 1)
            } ?: context.toastX("Unknown error happens.")
        }
    }
}
