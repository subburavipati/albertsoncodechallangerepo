package com.subbu.albertsonstask.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.subbu.albertsonstask.R
import com.subbu.albertsonstask.databinding.AcronymItemBinding
import com.subbu.albertsonstask.model.data.WordData

class WordsAdapter : ListAdapter<WordData, WordsAdapter.WordViewHolder>(
    object : DiffUtil.ItemCallback<WordData>() {
        override fun areItemsTheSame(oldItem: WordData, newItem: WordData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WordData, newItem: WordData): Boolean {
            return oldItem.longForm == newItem.longForm && oldItem.frequency == newItem.frequency && oldItem.since == newItem.since
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = AcronymItemBinding.inflate(LayoutInflater.from(parent.context))
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(getItem(position))

    class WordViewHolder(private val binding: AcronymItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: WordData) {
            binding.apply {
                with(word) {
                    tvAcronym.text = itemView.context.getString(R.string.acronym, longForm)
                    tvFrequency.text = itemView.context.getString(R.string.frequency, frequency.toString())
                    tvSince.text =  itemView.context.getString(R.string.since, since.toString())
                }
            }
        }
    }
}