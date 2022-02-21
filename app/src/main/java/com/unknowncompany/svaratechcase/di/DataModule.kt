package com.unknowncompany.svaratechcase.di

import com.unknowncompany.svaratechcase.data.repository.PokemonRepository
import com.unknowncompany.svaratechcase.data.source.local.PokemonLocalDataSource
import com.unknowncompany.svaratechcase.data.source.remote.PokemonRemoteDataSource
import com.unknowncompany.svaratechcase.data.source.remote.PokemonService
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single { PokemonRepository(get(), get()) }
}

val remoteDataSourceModule = module {
    single { PokemonRemoteDataSource(get()) }
}

val localDataSourceModule = module {
    single { PokemonLocalDataSource(get()) }
}

val serviceModule = module {
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }

    single { providePokemonService(get()) }
}
