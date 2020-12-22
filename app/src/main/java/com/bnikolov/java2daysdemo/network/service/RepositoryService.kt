package com.bnikolov.java2daysdemo.network.service

import com.bnikolov.java2daysdemo.network.model.Repository
import retrofit2.Call
import retrofit2.http.GET

interface RepositoryService {

    @GET("user/repos?type=owner")
    fun getRepositories(): Call<Array<Repository>>
}