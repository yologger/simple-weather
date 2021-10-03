package com.yologger.simpleweather.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleWeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}