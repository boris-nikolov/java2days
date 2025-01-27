package com.bnikolov.java2daysdemo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bnikolov.java2daysdemo.R
import com.bnikolov.java2daysdemo.adapter.RepositoryAdapter
import com.bnikolov.java2daysdemo.databinding.FragmentRepositoriesBinding
import com.bnikolov.java2daysdemo.db.model.RepositoryRealm
import com.bnikolov.java2daysdemo.view.ItemDecorationExcludeLast
import com.bnikolov.java2daysdemo.viewmodel.RepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment : Fragment(),
    RepositoryAdapter.OnRepositoryClickListener {

    private lateinit var binding: FragmentRepositoriesBinding

    private lateinit var repositoryAdapter: RepositoryAdapter

    @Inject
    lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater)

        repositoryAdapter = RepositoryAdapter()
        repositoryAdapter.setRepoClickListener(this)

        setDataBinding()
        observeLiveData()

        repositoryViewModel.getRepositories()

        return binding.root
    }

    override fun onRepositoryClicked(view: View, repo: RepositoryRealm) {
        val pullRequestAction = RepositoriesFragmentDirections.actionShowPullRequests(repo)
        view.findNavController().navigate(pullRequestAction)
    }

    private fun setDataBinding() {
        binding.apply {
            repoList.addItemDecoration(ItemDecorationExcludeLast(binding.root.context))
            repoList.adapter = repositoryAdapter
        }
    }

    private fun observeLiveData() {
        repositoryViewModel.repositoriesLiveData.observe(viewLifecycleOwner) {
            Log.e("MY_TEST_LOG", "Received ${it.size} repositories from data source")
            repositoryAdapter.submitList(it)
            repositoryAdapter.notifyDataSetChanged()
        }

        repositoryViewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it?.getContentIfNotHandled()?.message ?: getString(R.string.general_error_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}