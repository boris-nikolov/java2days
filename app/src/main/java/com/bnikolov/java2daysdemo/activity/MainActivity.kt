package com.bnikolov.java2daysdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bnikolov.java2daysdemo.R
import com.bnikolov.java2daysdemo.adapter.RepositoryAdapter
import com.bnikolov.java2daysdemo.databinding.ActivityMainBinding
import com.bnikolov.java2daysdemo.viewmodel.RepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repositoryAdapter: RepositoryAdapter

    @Inject
    lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repositoryAdapter = RepositoryAdapter()

        setDataBinding()
        observeLiveData()
        repositoryViewModel.getRepositories()
    }

    private fun setDataBinding() {
        binding.repoList.adapter = repositoryAdapter
    }

    private fun observeLiveData() {
        repositoryViewModel.repositoriesLiveData.observe(this) {
            repositoryAdapter.submitList(it.getContentIfNotHandled())
        }

        repositoryViewModel.errorMessageLiveData.observe(this) {
            Snackbar.make(
                binding.root,
                it?.getContentIfNotHandled()?.message ?: getString(R.string.general_error_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}