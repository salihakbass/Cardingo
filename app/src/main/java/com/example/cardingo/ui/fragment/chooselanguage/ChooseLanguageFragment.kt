package com.example.cardingo.ui.fragment.chooselanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentChooseLanguageBinding
import com.example.cardingo.databinding.FragmentSplashBinding



class ChooseLanguageFragment : Fragment() {
    private var _binding: FragmentChooseLanguageBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAnimations()

    }

    private fun loadAnimations() {
        val slideAnimationLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_left)
        val slideAnimationRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_right)

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