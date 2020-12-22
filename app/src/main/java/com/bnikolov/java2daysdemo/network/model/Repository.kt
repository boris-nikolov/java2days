package com.bnikolov.java2daysdemo.network.model

import java.io.Serializable

data class Repository(
    var id: String? = null,
    var name: String? = null,
    var private: Boolean? = false
) : Serializable