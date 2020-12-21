package com.bnikolov.java2daysdemo.network.model.response

import com.bnikolov.java2daysdemo.network.model.Repository

data class RepositoryResponse(val repositories: Array<Repository>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RepositoryResponse

        if (!repositories.contentEquals(other.repositories)) return false

        return true
    }

    override fun hashCode(): Int {
        return repositories.contentHashCode()
    }
}
