package com.ericdev.goshopping.feature_onboarding.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericdev.goshopping.databinding.OnboardingViewPagerPage3Binding

class OnBoardingPageThree : Fragment() {
    lateinit var binding: OnboardingViewPagerPage3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerPage3Binding.inflate(layoutInflater)
        return binding.root
    }
}