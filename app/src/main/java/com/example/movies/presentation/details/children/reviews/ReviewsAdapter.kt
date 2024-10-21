package com.example.movies.presentation.details.children.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellReviewBinding
import com.example.movies.domain.entities.Review
import com.example.movies.presentation.details.children.reviews.ReviewsAdapter.ReviewsViewHolder


class ReviewsAdapter : PagingDataAdapter<Review, ReviewsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        return ReviewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReviewsViewHolder(private val binding: CellReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ReviewsViewHolder {
                val binding =
                    CellReviewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return ReviewsViewHolder(binding)
            }
        }

        fun bind(item: Review?) {
            binding.review = item
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Review, newItem: Review) =
            oldItem == newItem

    }

}