package com.example.movies.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellVideoBinding
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.VideosAdapter.VideoViewHolder

class VideosAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<Movie, VideoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VideoViewHolder(private val binding: CellVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onItemClickListener: OnItemClickListener): VideoViewHolder {
                val binding =
                    CellVideoBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.onItemClickListener = onItemClickListener
                return VideoViewHolder(binding)
            }
        }

        fun bind(item: Movie?) {
            binding.video = item
        }
    }

    class OnItemClickListener(val listener: (video: Movie) -> Unit) {
        fun onItemClick(video: Movie) {
            listener(video)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }

}