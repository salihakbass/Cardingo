package com.example.cardingo.ui.fragment.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardingo.R
import com.example.cardingo.databinding.FragmentSplashBinding
import com.example.cardingo.ui.adapter.SplashAdapter


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: SplashAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        // Tanıtım ekranı görülmüş mü kontrol ediliyor
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isIntroSeen = sharedPreferences.getBoolean("isIntroSeen", false)

        // Eğer tanıtım ekranı daha önce görülmüşse, doğrudan WordsFragment'a yönlendiriyoruz
        if (isIntroSeen) {
            findNavController().navigate(R.id.action_splashFragment_to_chooseLanguageFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!_binding!!.root.isLaidOut) {
            initViewPagerAdapter()
        }
    }

    private val fragmentList = arrayListOf(
        TabAFragment(), TabBFragment(), TabCFragment(),TabDFragment()
    )
    private fun initViewPagerAdapter(){
        // Adapter init
        val viewPager = binding.vpHome
        adapter = SplashAdapter(childFragmentManager, viewLifecycleOwner.lifecycle,fragmentList)
        viewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.vpHome)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}