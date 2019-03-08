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

import android.content.Context
import com.no1.taiwan.stationmusicfm.data.data.DataMapperPool
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.data.datastores.LocalDataStore
import com.no1.taiwan.stationmusicfm.data.datastores.RemoteDataStore
import com.no1.taiwan.stationmusicfm.data.delegates.DataMapperDelegate
import com.no1.taiwan.stationmusicfm.data.delegates.DataMapperDigger
import com.no1.taiwan.stationmusicfm.data.local.config.MusicDatabase
import com.no1.taiwan.stationmusicfm.data.repositories.HistoryDataRepository
import com.no1.taiwan.stationmusicfm.data.repositories.LastFmDataRepository
import com.no1.taiwan.stationmusicfm.data.repositories.MusicBankDataRepository
import com.no1.taiwan.stationmusicfm.domain.repositories.HistoryRepository
import com.no1.taiwan.stationmusicfm.domain.repositories.LastFmRepository
import com.no1.taiwan.stationmusicfm.domain.repositories.MusicBankRepository
import com.no1.taiwan.stationmusicfm.domain.repositories.OthersRepository
import com.no1.taiwan.stationmusicfm.internal.di.tags.InstanceTag.JSOUP
import com.no1.taiwan.stationmusicfm.internal.di.tags.InstanceTag.LOCAL
import com.no1.taiwan.stationmusicfm.internal.di.tags.InstanceTag.REMOTE
import org.kodein.di.Kodein.Module
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * To provide the necessary objects into the repository.
 */
object RepositoryModule {
    fun repositoryProvider(context: Context) = Module("Repository Module") {
        // The necessary providers are from the service provider.
        import(ServiceModule.serviceProvider(context))

        bind<DataStore>(REMOTE) with singleton {
            RemoteDataStore(instance(),
                            instance(),
                            instance(),
                            instance(JSOUP),
                            context)
        }
        bind<DataStore>(LOCAL) with singleton {
            LocalDataStore(instance<MusicDatabase>().createRankingDao(),
                           instance<MusicDatabase>().createSearchHistoryDao(),
                           instance())
        }
        /** Mapper Pool for providing all data mappers */
        bind<DataMapperPool>() with singleton { instance<DataMapperEntries>().toMap() }
        bind<DataMapperDigger>() with singleton { DataMapperDelegate(instance()) }

        // Repositories into mapper
        bind<OthersRepository>() with singleton {
            object : OthersRepository {}
        }
        bind<HistoryRepository>() with singleton {
            HistoryDataRepository(instance(LOCAL), instance())
        }
        bind<LastFmRepository>() with singleton {
            LastFmDataRepository(instance(LOCAL), instance(REMOTE), instance())
        }
        bind<MusicBankRepository>() with singleton {
            MusicBankDataRepository(instance(LOCAL), instance(REMOTE), instance())
        }
    }
}
