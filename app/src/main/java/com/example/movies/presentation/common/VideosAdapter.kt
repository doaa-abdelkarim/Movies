package com.example.movies.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellVideoBinding
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.TVShow
import com.example.movies.domain.entities.Video
import com.example.movies.presentation.common.VideosAdapter.VideoViewHolder

class VideosAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<Video, VideoViewHolder>(DiffCallback()) {

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

        fun bind(item: Video?) {
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
//            oldItem.equals(newItem)
            if (newItem is Movie)
                (oldItem as Movie) == newItem
            else
                (oldItem as TVShow) == (newItem as TVShow)
    }

}