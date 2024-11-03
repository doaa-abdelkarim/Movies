package com.example.movies.presentation.home.children.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellFavoriteBinding
import com.example.movies.domain.entities.Favorite


class FavoritesAdapter :
    ListAdapter<Favorite, FavoritesAdapter.FavoritesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoritesViewHolder(private val binding: CellFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {
            binding.favorite = favorite
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

    class DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem.movieId == newItem.movieId

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem == newItem

    }

}