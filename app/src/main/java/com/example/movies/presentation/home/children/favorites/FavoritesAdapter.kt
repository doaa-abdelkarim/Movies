package com.example.movies.presentation.home.children.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellFavoriteBinding
import com.example.movies.domain.entities.BaseVideo


class FavoritesAdapter :
    ListAdapter<BaseVideo, FavoritesAdapter.FavoritesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoritesViewHolder(private val binding: CellFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: BaseVideo) {
            binding.video = video
        }

        companion object {
            fun from(
                parent: ViewGroup
            ): FavoritesViewHolder {
                val binding =
                    CellFavoriteBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return FavoritesViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseVideo>() {
        override fun areItemsTheSame(oldItem: BaseVideo, newItem: BaseVideo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BaseVideo, newItem: BaseVideo) =
            oldItem.equals(newItem)

    }

}