package com.bnikolov.java2daysdemo

import android.app.Application
import com.bnikolov.java2daysdemo.network.manager.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Java2DaysDemo : Application() {

    override fun onCreate() {
        super.onCreate()

        ConnectivityManager.init(this)
    }
}