package com.example.cardingo.ui.fragment.words

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.cardingo.MainActivity
import com.example.cardingo.R
import com.example.cardingo.data.entity.Words
import com.example.cardingo.databinding.FragmentWordsBinding
import com.example.cardingo.ui.adapter.WordsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import java.io.InputStreamReader


class WordsFragment : Fragment() {
    private lateinit var binding: FragmentWordsBinding
    private var list = ArrayList<Words>()
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWordsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvChangeLanguage.setOnClickListener {
            navigateToChooseLanguageFragment()
        }
        binding.tvChangeLevel.setOnClickListener {
            navigateToChooseLevelFragment()
        }

        val levelPreferences =
            requireContext().getSharedPreferences("SelectedLevel", Context.MODE_PRIVATE)
        val selectedLevel = levelPreferences.getString("level", "A1")
        val languagePreferences =
            requireContext().getSharedPreferences("SelectedLanguage", Context.MODE_PRIVATE)
        val selectedLanguage = languagePreferences.getString("language", "English")

        sharedPreferences =
            requireContext().getSharedPreferences("LearnedWord", Context.MODE_PRIVATE)

        bottomNav = (activity as MainActivity).findViewById(R.id.bottomNav)
        bottomNav.visibility = View.VISIBLE

        list = loadItemsFromJson(this)

        val gson = Gson()
        val savedWordsSet = sharedPreferences.getStringSet("savedWords", null)
        val savedIds = savedWordsSet?.map {
            val word = gson.fromJson(it, Words::class.java)
            word.id
        } ?: emptyList()


        val filteredWordList = list.filterNot { word -> savedIds.contains(word.id) }
            .filter { word -> word.level == selectedLevel && word.language == selectedLanguage }

        val mutableList = filteredWordList.toMutableList()

        val adapter = WordsAdapter(mutableList, sharedPreferences)
        binding.vpSlider.adapter = adapter




        binding.swipeRefreshLayout.setOnRefreshListener {
            mutableList.shuffle()
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()
        }




    }

    private fun loadItemsFromJson(context: WordsFragment): ArrayList<Words> {
        val inputStream = context.resources.openRawResource(R.raw.words)
        val reader = InputStreamReader(inputStream)

        val itemType = object : TypeToken<List<Words>>() {}.type
        return Gson().fromJson(reader, itemType)
    }

    private fun navigateToChooseLanguageFragment() {
        val action = WordsFragmentDirections.actionWordsFragmentToChooseLanguageFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun navigateToChooseLevelFragment() {
        val action = WordsFragmentDirections.actionWordsFragmentToChooseLevelFragment()
        binding.root.findNavController().navigate(action)
    }


}