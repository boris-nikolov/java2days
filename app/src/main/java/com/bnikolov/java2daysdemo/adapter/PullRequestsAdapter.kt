package com.bnikolov.java2daysdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnikolov.java2daysdemo.databinding.ListItemPullRequestBinding
import com.bnikolov.java2daysdemo.network.model.PullRequest

class PullRequestsAdapter(private val context: Context) :
    ListAdapter<PullRequest, PullRequestsAdapter.ViewHolder>(PullRequestDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.bind(repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemPullRequestBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    class ViewHolder(private val binding: ListItemPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: PullRequest
        ) {
            binding.pullRequest = item
        }
    }
}

private class PullRequestDiffCallback : DiffUtil.ItemCallback<PullRequest>() {

    override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
        return oldItem == newItem
    }
}