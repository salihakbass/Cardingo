package com.example.cardingo.ui.fragment.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentTabDBinding


class TabDFragment : Fragment() {
    private lateinit var binding: FragmentTabDBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabDBinding.inflate(inflater, container, false)
        // Tanıtım ekranlarının görüldüğünü kaydediyoruz
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isIntroSeen", true).apply()

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_chooseLanguageFragment)
        }
        return binding.root
    }

}