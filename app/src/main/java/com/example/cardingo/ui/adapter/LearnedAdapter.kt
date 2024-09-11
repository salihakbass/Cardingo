package com.example.cardingo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.LearnedViewBinding

class LearnedAdapter(
    val itemsList: MutableList<Words>,
    private val onRemoveClick: ((Words) -> Unit)? = null
) : RecyclerView.Adapter<LearnedAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LearnedViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Words) {
            val resourceId = itemView.context.resources.getIdentifier(item.image, "drawable", itemView.context.packageName)
            val resourceIdCountry = itemView.context.resources.getIdentifier(item.country, "drawable", itemView.context.packageName)
            with(binding) {
                ivWordLearned.setImageResource(resourceId)
                tvWordLearned.text = item.word + "(${item.level})"
                tvWordTurkishLearned.text = item.turkishWord
                ivCountry.setImageResource(resourceIdCountry)
                btnUnlearned.setOnClickListener {
                    onRemoveClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LearnedViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }


}