package com.example.cardingo.ui.fragment.chooselanguage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentChooseLanguageBinding


class ChooseLanguageFragment : Fragment() {
    private var _binding: FragmentChooseLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLanguageBinding.inflate(layoutInflater)

        val sharedPreferences =
            requireContext().getSharedPreferences("SelectedLanguage", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.ivEnglish.setOnClickListener {
            editor.putString("language", "English")
            editor.apply()

            val action =
                ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToChooseLevelFragment()
            findNavController().navigate(action)
        }
        binding.ivSpanish.setOnClickListener {
            editor.putString("language", "Spanish")
            editor.apply()

            val action =
                ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToChooseLevelFragment()
            findNavController().navigate(action)
        }
        binding.ivFrench.setOnClickListener {
            editor.putString("language", "French")
            editor.apply()

            val action =
                ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToChooseLevelFragment()
            findNavController().navigate(action)
        }
        binding.ivGerman.setOnClickListener {
            editor.putString("language", "German")
            editor.apply()

            val action =
                ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToChooseLevelFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAnimations()
    }

    private fun loadAnimations() {
        val slideAnimationLeft =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_left)
        val slideAnimationRight =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_right)

        binding.ivEnglish.startAnimation(slideAnimationLeft)
        binding.tvEnglish.startAnimation(slideAnimationLeft)
        binding.ivFrench.startAnimation(slideAnimationLeft)
        binding.tvFrench.startAnimation(slideAnimationLeft)
        binding.ivGerman.startAnimation(slideAnimationRight)
        binding.tvGerman.startAnimation(slideAnimationRight)
        binding.ivSpanish.startAnimation(slideAnimationRight)
        binding.tvSpanish.startAnimation(slideAnimationRight)
    }
}