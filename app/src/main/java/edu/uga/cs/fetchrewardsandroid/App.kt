package edu.uga.cs.fetchrewardsandroid

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import edu.uga.cs.fetchrewardsandroid.koin_dependency_injection.getNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(getNetworkModule()))
        }
    }
}