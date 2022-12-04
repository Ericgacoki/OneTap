package com.ericdev.goshopping.feature_onboarding.presentation.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericdev.goshopping.databinding.FragmentViewpagerBinding
import com.ericdev.goshopping.feature_onboarding.presentation.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment() {
    lateinit var binding: FragmentViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewpagerBinding.inflate(layoutInflater)

        val onBoardingFragments = arrayListOf<Fragment>(
            OnBoardingViewPagerPage1(),
            OnBoardingViewPagerPage2(),
            OnBoardingViewPagerPage3(),
        )

        val viewPagerAdapter = ViewPagerAdapter(
            onBoardingFragments,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.onBoardingViewpager.adapter = viewPagerAdapter

        return binding.root
    }
}