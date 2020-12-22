package com.bnikolov.java2daysdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnikolov.java2daysdemo.databinding.ListItemRepositoryBinding
import com.bnikolov.java2daysdemo.db.model.RepositoryRealm

class RepositoryAdapter :
    ListAdapter<RepositoryRealm, RepositoryAdapter.ViewHolder>(RepositoryDiffCallback()) {

    private var onRepositoryClickListener: OnRepositoryClickListener? = null

    interface OnRepositoryClickListener {
        fun onRepositoryClicked(view: View, repo: RepositoryRealm)
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

    private fun createRepoClickListener(repo: RepositoryRealm) =
        View.OnClickListener {
            onRepositoryClickListener?.onRepositoryClicked(it, repo)
        }

    fun setRepoClickListener(listener: OnRepositoryClickListener) {
        this.onRepositoryClickListener = listener
    }

    class ViewHolder(private val binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: RepositoryRealm,
            listener: View.OnClickListener
        ) {
            binding.repo = item
            binding.onRepoClickListener = listener
        }
    }
}

private class RepositoryDiffCallback : DiffUtil.ItemCallback<RepositoryRealm>() {

    override fun areItemsTheSame(oldItem: RepositoryRealm, newItem: RepositoryRealm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryRealm, newItem: RepositoryRealm): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.isPrivate == newItem.isPrivate
    }
}