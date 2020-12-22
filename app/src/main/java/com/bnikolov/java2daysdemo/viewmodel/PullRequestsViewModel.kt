package com.bnikolov.java2daysdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.bnikolov.java2daysdemo.repository.PullRequestsRepository
import javax.inject.Inject

class PullRequestsViewModel @Inject constructor(
    private val repository: PullRequestsRepository
) : ViewModel() {

    val repositoriesLiveData = repository.pullRequestsLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    fun getPullRequests(repoName: String?) = repository.getPullRequests(repoName)
}