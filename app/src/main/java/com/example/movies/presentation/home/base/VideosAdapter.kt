package com.example.movies.presentation.home.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.base.adapter.BasePaginationAdapter
import com.example.movies.databinding.ItemVideoBinding
import com.example.movies.domain.models.Video

class VideosAdapter(private val onItemClickListener: OnItemClickListener) :
    BasePaginationAdapter<Video>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOADING_FOOTER)
            ListLoadingViewHolder.from(parent)
        else
            VideoViewHolder.from(parent, onItemClickListener)
    }

    class VideoViewHolder(private val binding: ItemVideoBinding) :
        BaseViewHolder<Video>(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onItemClickListener: OnItemClickListener): VideoViewHolder {
                val binding =
                    ItemVideoBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.onItemClickListener = onItemClickListener
                return VideoViewHolder(binding)
            }
        }

        override fun bind(item: Video) {
            binding.video = item
        }
    }

    class OnItemClickListener(val listener: (video: Video) -> Unit) {
        fun onItemClick(video: Video) {
            listener(video)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Video, newItem: Video) =
            oldItem.equals(newItem)
    }

}