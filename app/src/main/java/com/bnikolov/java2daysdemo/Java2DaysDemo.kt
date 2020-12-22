package com.bnikolov.java2daysdemo

import android.app.Application
import com.bnikolov.java2daysdemo.network.manager.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class Java2DaysDemo : Application() {

    override fun onCreate() {
        super.onCreate()

        ConnectivityManager.init(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}