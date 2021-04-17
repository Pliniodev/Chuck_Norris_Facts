package com.pliniodev.chucknorrisfacts

import android.app.Application
import com.pliniodev.chucknorrisfacts.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            /**
             * (Level.NONE) foi usado devido a este bug na versão 2.0.0 do koin
             * --> No virtual method elapsedNow()D in class Lkotlin/time/TimeMark...
             * ISSUE relacionada https://github.com/InsertKoinIO/koin/issues/847
             * parece ter sido resolvido na versão 2.2.0-alpha-1
             */
            androidLogger(Level.NONE)
            androidContext(this@MyApp)

            modules(
                mainModule,
                searchModule,
                networkModule,
                repositoryModule,
                apiModule
            )
        }
    }
}