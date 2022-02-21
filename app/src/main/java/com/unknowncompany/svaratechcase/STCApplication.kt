package com.unknowncompany.svaratechcase

import android.app.Application
import com.unknowncompany.svaratechcase.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class STCApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidLogger(Level.ERROR)
            androidContext(this@STCApplication)
            modules(
                listOf(
                    networkModule,
                    serviceModule,
                    remoteDataSourceModule,
                    databaseModule,
                    localDataSourceModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

}