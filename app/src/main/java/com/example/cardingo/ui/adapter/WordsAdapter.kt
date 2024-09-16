package com.example.cardingo.ui.adapter

import android.content.SharedPreferences
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.MainSliderViewBinding
import com.google.gson.Gson
import java.util.Locale

class WordsAdapter(
    private var wordList: MutableList<Words>, private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<WordsAdapter.ViewHolder>(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var context: android.content.Context? = null



    inner class ViewHolder(var binding: MainSliderViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MainSliderViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context


        tts = TextToSpeech(context, this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordList[position]
        val binding = holder.binding
        val resourceId = holder.itemView.context.resources.getIdentifier(
            item.image, "drawable", holder.itemView.context.packageName
        )
        val resourceIdCountry = holder.itemView.context.resources.getIdentifier(
            item.country, "drawable", holder.itemView.context.packageName
        )
        with(binding) {
            ivWord.setImageResource(resourceId)
            tvEnglishWord.text = item.word
            tvTurkishWord.text = item.turkishWord
            tvSentence.text = item.sentence
            tvSentenceTurkish.text = item.turkishSentence
            imgCountry.setImageResource(resourceIdCountry)
            // ivSpeech tıklanınca İngilizce kelimeyi seslendir
            ivSpeech.setOnClickListener {
                speakOut(item.word)
            }
            ivFavorite.setOnClickListener {
                saveWordToSharedPreferences(item)
                removeItem(position)
                notifyDataSetChanged()
            }

        }
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

    private fun removeItem(position: Int) {
        if (position in wordList.indices) {
            wordList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    // TTS motoru başlatıldıktan sonra dil ayarlarını yapıyoruz
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // İngilizce dilini ayarlıyoruz
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                println("TTS: Dil desteklenmiyor.")
            }
        } else {
            println("TTS motoru başlatılamadı.")
        }
    }
    // TTS ile kelimeyi seslendiren fonksiyon
    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    // Adaptör kapanırken TTS motorunu serbest bırakıyoruz
    fun shutdownTTS() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

}