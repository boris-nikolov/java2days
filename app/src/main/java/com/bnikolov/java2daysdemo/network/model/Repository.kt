package com.bnikolov.java2daysdemo.network.model

import com.bnikolov.java2daysdemo.db.model.RepositoryRealm

data class Repository(
    var id: String? = null,
    var name: String? = null,
    var private: Boolean? = false
) {

    fun toLocalModel() = RepositoryRealm(
        id = this.id,
        name = this.name,
        isPrivate = this.private
    )
}