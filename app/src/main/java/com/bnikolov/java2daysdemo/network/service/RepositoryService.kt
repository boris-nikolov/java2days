package com.bnikolov.java2daysdemo.network.service

import com.bnikolov.java2daysdemo.network.model.PullRequest
import com.bnikolov.java2daysdemo.network.model.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {

    @GET("user/repos?type=owner")
    fun getRepositories(): Call<Array<Repository>>

    @GET("repos/boris-nikolov/{repoName}/pulls")
    fun getPullRequests(@Path("repoName") repoName: String): Call<Array<PullRequest>>
}