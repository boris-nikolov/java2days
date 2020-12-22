package com.bnikolov.java2daysdemo.extension

import com.bnikolov.java2daysdemo.data.util.RealmLiveData
import io.realm.RealmModel
import io.realm.RealmResults

fun <T : RealmModel> RealmResults<T>.asLiveData() =
    RealmLiveData(this)