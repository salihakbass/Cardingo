package com.example.cardingo.ui.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.MainSliderViewBinding
import com.example.cardingo.databinding.PopupLayoutBinding
import com.google.gson.Gson

class WordsAdapter(
    var wordList: MutableList<Words>,
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MainSliderViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MainSliderViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordList[position]
        val binding = holder.binding
        val resourceId = holder.itemView.context.resources.getIdentifier(
            item.image,
            "drawable",
            holder.itemView.context.packageName
        )
        val resourceIdCountry = holder.itemView.context.resources.getIdentifier(
            item.country,
            "drawable",
            holder.itemView.context.packageName
        )
        with(binding) {
            ivWord.setImageResource(resourceId)
            tvEnglishWord.text = item.word
            tvTurkishWord.text = item.turkishWord
            tvSentence.text = item.sentence
            tvSentenceTurkish.text = item.turkishSentence
            imgCountry.setImageResource(resourceIdCountry)

            tvEnglishWord.setOnClickListener {
                showPopUp(holder.itemView, item, position,binding)
            }
        }
    }

    private fun showPopUp(view: View, word: Words, position: Int,binding: MainSliderViewBinding) {
        val popupBinding = PopupLayoutBinding.inflate(LayoutInflater.from(view.context))
        val popupWindow = createPopupWindow(popupBinding)

        val location = IntArray(2)
        binding.tvEnglishWord.getLocationOnScreen(location)

        val xOffSet = location[0]
        val yOffSet = location[1] - popupWindow.height

        val xAdjustment = -50
        val yAdjustment = -180

        popupBinding.btnAdd.setOnClickListener {
            saveWordToSharedPreferences(word)
            animateRemoval(view, position)
            popupWindow.dismiss()
            notifyDataSetChanged()
        }
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xOffSet + xAdjustment, yOffSet + yAdjustment)
    }

    private fun createPopupWindow(popupBinding: PopupLayoutBinding): PopupWindow {
        return PopupWindow(
            popupBinding.root,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
    }

    private fun saveWordToSharedPreferences(word: Words) {
        val gson = Gson()
        val json = gson.toJson(word)
        val savedWords =
            sharedPreferences.getStringSet("savedWords", mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()

        savedWords.add(json)

        val editor = sharedPreferences.edit()
        editor.putStringSet("savedWords", savedWords).apply()
    }


    fun removeItem(position: Int) {
        if (position in wordList.indices) {
            wordList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun animateRemoval(view: View, position: Int) {
        view.animate()
            .translationY(-view.height.toFloat())
            .alpha(0.0f)
            .setDuration(300)
            .withEndAction {
                removeItem(position)
            }
    }
}