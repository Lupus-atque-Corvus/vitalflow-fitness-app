package com.example.vitalflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import androidx.work.WorkManager

@HiltAndroidApp
class VitalFlowApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        // Initialize WorkManager
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build()
        )
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}
