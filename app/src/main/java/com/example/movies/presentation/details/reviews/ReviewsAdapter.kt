package com.example.movies.presentation.details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.base.adapter.BasePaginationAdapter
import com.example.movies.databinding.ItemReviewBinding
import com.example.movies.domain.models.Review


class ReviewsAdapter : BasePaginationAdapter<Review>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOADING_FOOTER)
            ListLoadingViewHolder.from(parent)
        else
            ReviewsViewHolder.from(parent)
    }

     class ReviewsViewHolder(private val binding: ItemReviewBinding) :
        BaseViewHolder<Review>(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ReviewsViewHolder {
                val binding =
                    ItemReviewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return ReviewsViewHolder(binding)
            }
        }

         override fun bind(item: Review) {
             binding.review = item
         }

     }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review)  =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Review, newItem: Review) =
            oldItem == newItem

    }

}