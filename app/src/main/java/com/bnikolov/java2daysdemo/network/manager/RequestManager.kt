package com.bnikolov.java2daysdemo.network.manager

import com.bnikolov.java2daysdemo.network.service.RepositoryService
import javax.inject.Inject

class RequestManager @Inject constructor(
    private val repositoryService: RepositoryService
) {

    fun getRepositories() = repositoryService.getRepositories()

    fun getPullRequests(repoName: String) = repositoryService.getPullRequests(repoName)
}