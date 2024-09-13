package com.example.cardingo.ui.adapter

import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.LearnedViewBinding
import com.example.cardingo.databinding.PopupDeleteBinding
import com.google.gson.Gson

class LearnedAdapter(
    val itemsList: MutableList<Words>,
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<LearnedAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LearnedViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Words) {
            val resourceId = itemView.context.resources.getIdentifier(
                item.image,
                "drawable",
                itemView.context.packageName
            )
            val resourceIdCountry = itemView.context.resources.getIdentifier(
                item.country,
                "drawable",
                itemView.context.packageName
            )
            with(binding) {
                ivWordLearned.setImageResource(resourceId)
                tvWordLearned.text = item.word + "(${item.level})"
                tvWordTurkishLearned.text = item.turkishWord
                ivCountry.setImageResource(resourceIdCountry)
                ivWordLearned.setOnClickListener {
                    showPopUp(itemView, item, adapterPosition)
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

    private fun createPopupWindow(popupBinding: PopupDeleteBinding): PopupWindow {
        return PopupWindow(
            popupBinding.root,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
    }

    private fun showPopUp(view: View, word: Words, position: Int) {
        val popupBinding = PopupDeleteBinding.inflate(LayoutInflater.from(view.context))
        val popupWindow = createPopupWindow(popupBinding)

        val ımageResource = view.context.resources.getIdentifier(
            word.image,
            "drawable",
            view.context.packageName
        )

        popupBinding.popupImage.setImageResource(ımageResource)
        popupBinding.tvWord.text = word.word
        popupBinding.tvTurkishWord.text = word.turkishWord


        popupBinding.btnAdd.setOnClickListener {
            removeWordFromSharedPreferences(word)
            removeItem(position)
            popupWindow.dismiss()
            notifyDataSetChanged()
        }
        popupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0)
    }

    fun removeItem(position: Int) {
        if (position in itemsList.indices) {
            itemsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun removeWordFromSharedPreferences(word: Words) {
        val gson = Gson()
        val json = gson.toJson(word)

        val savedWordsSet =
            sharedPreferences.getStringSet("savedWords", mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        savedWordsSet.remove(json)

        val editor = sharedPreferences.edit()
        editor.putStringSet("savedWords", savedWordsSet).apply()

    }


}


