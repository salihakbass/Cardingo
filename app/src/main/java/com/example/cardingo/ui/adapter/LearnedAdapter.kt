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
        fun bind(item: Words, position: Int) {
            with(binding) {
                ivWordLearned.setImageResource(item.image)
                tvWordLearned.text = item.word + "(${item.level})"
                tvWordTurkishLearned.text = item.turkishWord
                btnUnlearned.setOnClickListener {
                    onRemoveClick?.invoke(item)
                    removeItemWithAnimation(this@ViewHolder, position)
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
        holder.bind(itemsList[position], position)
    }

    private fun removeItemWithAnimation(viewHolder: ViewHolder, position: Int) {
        val itemView = viewHolder.itemView
        itemView.animate()
            .translationX(-itemView.width.toFloat())
            .alpha(0.0f)
            .setDuration(300)
            .withEndAction {
                itemsList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemsList.size - position)
            }
    }
}