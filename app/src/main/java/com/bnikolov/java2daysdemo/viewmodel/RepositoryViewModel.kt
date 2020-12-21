package com.bnikolov.java2daysdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.bnikolov.java2daysdemo.repository.GithubRepoRepository
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(
    private val repository: GithubRepoRepository
) : ViewModel() {

    val repositoriesLiveData = repository.repositoriesLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    fun getRepositories() = repository.getRepositories()
}