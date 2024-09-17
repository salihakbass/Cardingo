package com.example.cardingo.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.LearnedViewBinding
import com.google.gson.Gson

class LearnedAdapter(
    private var itemsList: MutableList<Words>, private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<LearnedAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LearnedViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Words) {
            val resourceId = itemView.context.resources.getIdentifier(
                item.image, "drawable", itemView.context.packageName
            )
            val resourceIdCountry = itemView.context.resources.getIdentifier(
                item.country, "drawable", itemView.context.packageName
            )
            with(binding) {
                ivWordLearned.setImageResource(resourceId)
                tvWordLearned.text = item.word
                tvWordTurkishLearned.text = "-  " + item.turkishWord
                tvLevel.text = ("(" + item.level + ")")
                ivDelete.setOnClickListener {
                    showDeleteConfirmationDialog(itemView.context, itemsList.indexOf(item),item.word)
                }
                ivCountry.setImageResource(resourceIdCountry)
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

    private fun showDeleteConfirmationDialog(context: Context, position: Int, word: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(word)
        builder.setMessage("Silmek İstediğinize Emin misiniz?")

        builder.setPositiveButton("Evet") { dialog, which ->
            removeWordFromSharedPreferences(itemsList[position])
            removeItem(position)
        }

        builder.setNegativeButton("Hayır") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun removeItem(position: Int) {
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

    // Listeyi güncellemek için fonksiyon
    fun updateList(newList: MutableList<Words>) {
        itemsList = newList
        notifyDataSetChanged()
    }
}


