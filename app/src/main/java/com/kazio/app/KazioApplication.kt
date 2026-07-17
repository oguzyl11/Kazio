package com.kazio.app

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class KazioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        
        // Initialize AdMob
        MobileAds.initialize(this) {}
        
        setupGlobalExceptionHandler()
    }

    private fun setupGlobalExceptionHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Timber.e(throwable, "Uncaught exception crashed the app")
            // Here you can do emergency saves or log sending before crash
            // ...
            
            // Hand over to the default handler (which will kill the app)
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}
