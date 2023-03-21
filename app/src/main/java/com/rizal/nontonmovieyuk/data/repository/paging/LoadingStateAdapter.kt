package com.rizal.nontonmovieyuk.data.repository.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rizal.nontonmovieyuk.databinding.AdapterLoadStateBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterLoadStateBinding
            .inflate(inflater, parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bindView()
    }

    inner class LoadStateViewHolder(val binding: AdapterLoadStateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.actionRetry.isVisible = loadState !is LoadState.Loading
            binding.actionRetry.setOnClickListener { retry.invoke() }
        }
    }
}