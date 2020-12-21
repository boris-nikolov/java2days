package com.bnikolov.java2daysdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnikolov.java2daysdemo.databinding.ListItemRepositoryBinding
import com.bnikolov.java2daysdemo.network.model.Repository

class RepositoryAdapter :
    ListAdapter<Repository, RepositoryAdapter.ViewHolder>(RepositoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.bind(repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    class ViewHolder(private val binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repository) {
            binding.repo = item
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