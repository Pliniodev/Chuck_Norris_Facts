package com.pliniodev.chucknorrisfacts

import android.app.Application
import com.pliniodev.chucknorrisfacts.di.mainModule
import com.pliniodev.chucknorrisfacts.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)

            modules(
                mainModule,
                searchModule
            )
        }
    }
}