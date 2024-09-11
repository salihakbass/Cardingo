package com.example.cardingo.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentTabCBinding


class TabCFragment : Fragment() {
    private lateinit var binding : FragmentTabCBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabCBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_chooseLanguageFragment)
        }
        return binding.root
    }


}