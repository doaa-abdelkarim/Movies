package com.example.movies.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellMovieBinding
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.MoviesAdapter.MoviesViewHolder

class MoviesAdapter(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<Movie, MoviesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.from(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MoviesViewHolder(private val binding: CellMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener
            ): MoviesViewHolder {
                val binding =
                    CellMovieBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.onItemClickListener = onItemClickListener
                return MoviesViewHolder(binding)
            }
        }

        fun bind(item: Movie?) {
            binding.movie = item
        }
    }

    class OnItemClickListener(val listener: (movie: Movie) -> Unit) {
        fun onItemClick(movie: Movie) {
            listener(movie)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }

}