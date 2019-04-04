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

import android.content.Context
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrapid.kotlinshaver.cast
import com.devrapid.kotlinshaver.uiSwitch
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.no1.taiwan.stationmusicfm.R
import com.no1.taiwan.stationmusicfm.domain.parameters.EmptyParams
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchPlaylistsCase
import com.no1.taiwan.stationmusicfm.domain.usecases.FetchPlaylistsReq
import com.no1.taiwan.stationmusicfm.entities.PreziMapperPool
import com.no1.taiwan.stationmusicfm.entities.mappers.playlist.PlaylistInfoToBottomEntityPMapper
import com.no1.taiwan.stationmusicfm.internal.di.Dispatcher
import com.no1.taiwan.stationmusicfm.internal.di.tags.ObjectLabel.LINEAR_LAYOUT_VERTICAL
import com.no1.taiwan.stationmusicfm.utils.presentations.exec
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicAdapter
import com.no1.taiwan.stationmusicfm.widget.components.recyclerview.MusicVisitables
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class PlaylistBottomSheetDialog(
    context: Context,
    @StyleRes theme: Int
) : BottomSheetDialog(context, theme), KodeinAware {
    /** A Kodein Aware class must be within reach of a [Kodein] object. */
    override val kodein = Dispatcher.importIntoBottomSheet(context)
    private val fetchPlaylistsCase: FetchPlaylistsCase by instance()
    private val mapperPool: PreziMapperPool by instance()
    private val playlistMapper by lazy {
        cast<PlaylistInfoToBottomEntityPMapper>(mapperPool[PlaylistInfoToBottomEntityPMapper::class.java])
    }
    private val adapter: MusicAdapter by instance()
    private val layoutManager: LinearLayoutManager by instance(LINEAR_LAYOUT_VERTICAL)

    override fun onStart() {
        super.onStart()
        find<RecyclerView>(R.id.rv_playlists).apply {
            adapter = this@PlaylistBottomSheetDialog.adapter
            layoutManager = this@PlaylistBottomSheetDialog.layoutManager
        }
        GlobalScope.launch {
            val playlist = fetchPlaylistsCase.exec(FetchPlaylistsReq(EmptyParams())).map(playlistMapper::toEntityFrom)
            uiSwitch {
                adapter.append(cast<MusicVisitables>(playlist))
            }
        }
    }
}
