package com.bnikolov.java2daysdemo.network.manager

import com.bnikolov.java2daysdemo.network.service.PullRequestService
import com.bnikolov.java2daysdemo.network.service.RepositoryService
import javax.inject.Inject

class RequestManager @Inject constructor(
    private val repositoryService: RepositoryService,
    private val pullRequestService: PullRequestService
) {

    fun getRepositories() = repositoryService.getRepositories()

    fun getPullRequests(repoName: String) = pullRequestService.getPullRequests(repoName)
}