package com.example.movies.presentation.details.clips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.movies.base.adapter.BaseAdapter
import com.example.movies.databinding.ItemClipBinding
import com.example.movies.domain.models.Clip


class ClipsAdapter(private val onItemClickListener: OnItemClickListener) :
    BaseAdapter<Clip>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipsViewHolder {
        return ClipsViewHolder.from(parent, onItemClickListener)
    }

     class ClipsViewHolder(private val binding: ItemClipBinding) :
        BaseViewHolder<Clip>(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onItemClickListener: OnItemClickListener): ClipsViewHolder {
                val binding =
                    ItemClipBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.onItemClickListener = onItemClickListener
                return ClipsViewHolder(binding)
            }
        }

         override fun bind(item: Clip) {
             binding.clip = item
         }
     }

    class OnItemClickListener (val listener: (clip: Clip) -> Unit) {
        fun onItemClick(clip: Clip) {
            listener(clip)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Clip>() {
        override fun areItemsTheSame(oldItem: Clip, newItem: Clip)  =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Clip, newItem: Clip) =
            oldItem == newItem

    }

}