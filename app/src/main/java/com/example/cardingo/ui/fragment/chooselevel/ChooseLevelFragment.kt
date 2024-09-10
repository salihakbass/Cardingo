package com.example.cardingo.ui.fragment.chooselevel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentChooseLevelBinding


class ChooseLevelFragment : Fragment() {
    private lateinit var binding : FragmentChooseLevelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseLevelBinding.inflate(inflater, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("SelectedLevel", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val languagePreferences = requireContext().getSharedPreferences("SelectedLanguage", Context.MODE_PRIVATE)
        val selectedLanguage = languagePreferences.getString("language", "English")

        binding.btnAFirst.setOnClickListener {
            // Seçilen seviyeyi kaydet
            editor.putString("level", "A1")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("A1","$selectedLanguage")
            findNavController().navigate(action)
        }

        binding.btnASecond.setOnClickListener {
            editor.putString("level", "A2")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("A2","$selectedLanguage")
            findNavController().navigate(action)
        }

        binding.btnBFirst.setOnClickListener {
            editor.putString("level", "B1")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("B1","$selectedLanguage")
            findNavController().navigate(action)
        }

        binding.btnBSecond.setOnClickListener {
            editor.putString("level", "B2")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("B2","$selectedLanguage")
            findNavController().navigate(action)
        }

        binding.btnCFirst.setOnClickListener {
            editor.putString("level", "C1")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("C1","$selectedLanguage")
            findNavController().navigate(action)
        }

        binding.btnCSecond.setOnClickListener {
            editor.putString("level", "C2")
            editor.apply()

            val action = ChooseLevelFragmentDirections.actionChooseLevelFragmentToWordsFragment("C2","$selectedLanguage")
            findNavController().navigate(action)
        }
        return binding.root
    }

}