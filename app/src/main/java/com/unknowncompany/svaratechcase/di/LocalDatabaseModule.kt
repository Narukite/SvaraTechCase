package com.unknowncompany.svaratechcase.di

import androidx.room.Room
import com.unknowncompany.svaratechcase.BuildConfig
import com.unknowncompany.svaratechcase.data.source.local.PokemonDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<PokemonDatabase>().pokemonDao() }
    single {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE_KEY.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "${PokemonDatabase::class.simpleName}.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}