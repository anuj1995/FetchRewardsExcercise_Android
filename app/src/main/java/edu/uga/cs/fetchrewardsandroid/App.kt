package edu.uga.cs.fetchrewardsandroid

import android.app.Application
import edu.uga.cs.fetchrewardsandroid.KoinDependencyInjection.getNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(getNetworkModule()))
        }
    }
}