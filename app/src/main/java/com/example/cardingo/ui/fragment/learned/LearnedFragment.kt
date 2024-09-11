package com.example.cardingo.ui.fragment.learned

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.FragmentLearnedBinding
import com.example.cardingo.ui.adapter.LearnedAdapter
import com.google.gson.Gson


class LearnedFragment : Fragment() {
    private lateinit var binding: FragmentLearnedBinding
    private lateinit var learnedAdapter: LearnedAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedWordsList: MutableList<Words>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("LearnedWord", Context.MODE_PRIVATE)

        loadSavedWords()

        val recyclerView = binding.rvLearned
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        learnedAdapter = LearnedAdapter(savedWordsList) { selectedWord ->
            removeWordFromSharedPreferences(selectedWord)
        }
        recyclerView.adapter = learnedAdapter


    }

    private fun loadSavedWords() {
        val gson = Gson()
        val savedWordsSet =
            sharedPreferences.getStringSet("savedWords", mutableSetOf()) ?: mutableSetOf()

        if (savedWordsSet.isNotEmpty()) {
            savedWordsList = savedWordsSet.map { json ->
                gson.fromJson(json, Words::class.java)
            }.toMutableList()
        } else {
            savedWordsList = mutableListOf()
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

        savedWordsList.remove(word)
        learnedAdapter.notifyDataSetChanged()
    }

    private fun isEmpty() {
        if (learnedAdapter.itemCount == 0) {
            binding.rvLearned.visibility = View.GONE
            binding.lottieLayout.visibility = View.VISIBLE
        } else {
            binding.rvLearned.visibility = View.VISIBLE
            binding.lottieLayout.visibility = View.GONE
        }
    }

}