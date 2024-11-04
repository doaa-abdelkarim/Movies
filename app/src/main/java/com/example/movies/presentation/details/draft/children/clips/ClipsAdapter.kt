package com.example.movies.presentation.details.draft.children.clips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.CellClipBinding
import com.example.movies.domain.entities.Clip
import com.example.movies.presentation.details.draft.children.clips.ClipsAdapter.ClipsViewHolder


class ClipsAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Clip, ClipsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipsViewHolder {
        return ClipsViewHolder.from(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ClipsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ClipsViewHolder(private val binding: CellClipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener
            ): ClipsViewHolder {
                val binding =
                    CellClipBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                binding.onItemClickListener = onItemClickListener
                return ClipsViewHolder(binding)
            }
        }

        fun bind(item: Clip) {
            binding.clip = item
        }
    }

    class OnItemClickListener(val listener: (clip: Clip) -> Unit) {
        fun onItemClick(clip: Clip) {
            listener(clip)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Clip>() {
        override fun areItemsTheSame(oldItem: Clip, newItem: Clip) =
            oldItem.clipId == newItem.clipId

        override fun areContentsTheSame(oldItem: Clip, newItem: Clip) =
            oldItem == newItem

    }

}