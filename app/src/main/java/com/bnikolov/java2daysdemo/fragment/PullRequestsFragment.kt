package com.bnikolov.java2daysdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bnikolov.java2daysdemo.R
import com.bnikolov.java2daysdemo.adapter.PullRequestsAdapter
import com.bnikolov.java2daysdemo.databinding.FragmentPullRequestsBinding
import com.bnikolov.java2daysdemo.viewmodel.PullRequestsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PullRequestsFragment : Fragment() {

    lateinit var binding: FragmentPullRequestsBinding

    private lateinit var pullRequestsAdapter: PullRequestsAdapter

    @Inject
    lateinit var pullRequestsViewModel: PullRequestsViewModel

    private val args: PullRequestsFragmentArgs by navArgs()

    // yet another comment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPullRequestsBinding.inflate(inflater)

        pullRequestsAdapter = PullRequestsAdapter(requireContext())

        setDataBinding()
        observeLiveData()

        pullRequestsViewModel.getPullRequests(args.repo.name)

        return binding.root
    }

    private fun setDataBinding() {
        binding.apply {
            repository = args.repo.name
            pullRequestsList.adapter = pullRequestsAdapter
        }
    }

    private fun observeLiveData() {
        pullRequestsViewModel.repositoriesLiveData.observe(viewLifecycleOwner) { it ->
            pullRequestsAdapter.submitList(
                it.getContentIfNotHandled()?.sortedByDescending { it?.state })
        }

        pullRequestsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                it?.getContentIfNotHandled()?.message ?: getString(R.string.general_error_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}