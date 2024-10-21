package com.example.movies.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ItemLoaderBinding
import com.example.movies.presentation.common.LoaderStateAdapter.LoaderViewHolder

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.from(parent, retry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    /**
     * view holder class for footer loader and error state handling
     */
    class LoaderViewHolder(private val binding: ItemLoaderBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val binding =
                    ItemLoaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.buttonRetry.setOnClickListener {
                    retry()
                }
                return LoaderViewHolder(binding, retry)
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.motionLayoutLoader.transitionToEnd()
            } else {
                binding.motionLayoutLoader.transitionToStart()
            }
        }
    }
}