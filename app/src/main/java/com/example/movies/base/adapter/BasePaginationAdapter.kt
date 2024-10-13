package com.example.movies.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ItemLoadingBinding


abstract class BasePaginationAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    BaseAdapter<T>(diffCallback) {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING_FOOTER = 1
    }

    var isLoadingProgressBarVisible = false

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM)
            super.onBindViewHolder(holder, position)
        else if (getItemViewType(position) == TYPE_LOADING_FOOTER)
            (holder as? ListLoadingViewHolder)?.setProgressBarVisibility(isLoadingProgressBarVisible)
    }

    override fun getItemCount(): Int =
        if (isLoadingProgressBarVisible) currentList.size + 1 else currentList.size

    override fun getItemViewType(position: Int) =
        if (currentList.size - 1 >= position) TYPE_ITEM else TYPE_LOADING_FOOTER

    fun setLoadingProgressBarVisibility(isVisible: Boolean) {
        isLoadingProgressBarVisible = isVisible
        notifyItemChanged(currentList.size)
    }

    class ListLoadingViewHolder(val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ListLoadingViewHolder {
                val binding =
                    ItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return ListLoadingViewHolder(binding)
            }
        }

        fun setProgressBarVisibility(isVisible: Boolean) {
            if (isVisible)
                binding.progressBarLoading.visibility = View.VISIBLE
            else
                binding.progressBarLoading.visibility = View.GONE
        }
    }
}