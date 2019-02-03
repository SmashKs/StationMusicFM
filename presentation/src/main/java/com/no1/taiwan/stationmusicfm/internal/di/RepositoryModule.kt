package com.no1.taiwan.stationmusicfm.internal.di

import android.content.Context
import com.no1.taiwan.stationmusicfm.data.data.DataMapperPool
import com.no1.taiwan.stationmusicfm.data.datastores.DataStore
import com.no1.taiwan.stationmusicfm.data.datastores.LocalDataStore
import com.no1.taiwan.stationmusicfm.data.datastores.RemoteDataStore
import com.no1.taiwan.stationmusicfm.data.repositories.LastFmDataRepository
import com.no1.taiwan.stationmusicfm.domain.repositories.LastFmRepository
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

        bind<DataStore>(REMOTE) with singleton { RemoteDataStore(instance()) }
        bind<DataStore>(LOCAL) with singleton { LocalDataStore(instance()) }
        /** Mapper Pool for providing all data mappers */
        bind<DataMapperPool>() with singleton { instance<DataMapperEntries>().toMap() }

        // Repositories into mapper
        bind<LastFmRepository>() with singleton {
            LastFmDataRepository(instance(LOCAL), instance(REMOTE), instance())
        }
    }
}
