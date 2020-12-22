package com.bnikolov.java2daysdemo.network.service

import com.bnikolov.java2daysdemo.network.model.PullRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PullRequestService {

    @GET("repos/boris-nikolov/{repoName}/pulls?state=all")
    fun getPullRequests(@Path("repoName") repoName: String): Call<Array<PullRequest>>
}