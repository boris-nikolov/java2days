package com.bnikolov.java2daysdemo.network.util

import okhttp3.Request

fun Request.signWithToken(token: String): Request =
    newBuilder().header("Authorization", "token $token").build()