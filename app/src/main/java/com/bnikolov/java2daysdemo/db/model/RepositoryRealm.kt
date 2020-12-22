package com.bnikolov.java2daysdemo.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class RepositoryRealm(
    @PrimaryKey
    var id: String? = null,
    var name: String? = null,
    var isPrivate: Boolean? = false
) : RealmObject(), Serializable