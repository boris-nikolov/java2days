package com.bnikolov.java2daysdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnikolov.java2daysdemo.databinding.ListItemRepositoryBinding
import com.bnikolov.java2daysdemo.network.model.Repository

class RepositoryAdapter(private val context: Context) :
    ListAdapter<Repository, RepositoryAdapter.ViewHolder>(RepositoryDiffCallback()) {

    private var onRepositoryClickListener: OnRepositoryClickListener? = null

    interface OnRepositoryClickListener {
        fun onRepositoryClicked(view: View, repo: Repository)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.bind(repository, createRepoClickListener(repository))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    private fun createRepoClickListener(repo: Repository) =
        View.OnClickListener {
            onRepositoryClickListener?.onRepositoryClicked(it, repo)
        }

    fun setRepoClickListener(listener: OnRepositoryClickListener) {
        this.onRepositoryClickListener = listener
    }

    class ViewHolder(private val binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Repository,
            listener: View.OnClickListener
        ) {
            binding.repo = item
            binding.onRepoClickListener = listener
        }
    }
}

private class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}