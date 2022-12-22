package com.ericdev.goshopping.feature_onboarding.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericdev.goshopping.databinding.FragmentViewpagerBinding
import com.ericdev.goshopping.feature_onboarding.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewpagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onBoardingFragments = arrayListOf<Fragment>(
            OnBoardingPageOne(),
            OnBoardingPageTwo(),
            OnBoardingPageThree()
        )
        val viewPagerAdapter = ViewPagerAdapter(
            onBoardingFragments,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val pagerIndicator = binding.pagerIndicator
        val viewPager = binding.onBoardingViewpager.also {
            it.adapter = viewPagerAdapter
        }

        TabLayoutMediator(pagerIndicator, viewPager) { tab, position -> }.attach()
    }
}
