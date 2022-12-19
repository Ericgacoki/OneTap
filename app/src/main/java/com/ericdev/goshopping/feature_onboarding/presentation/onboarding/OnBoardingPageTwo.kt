package com.ericdev.goshopping.feature_onboarding.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericdev.goshopping.databinding.OnboardingViewPagerPage2Binding

class OnBoardingPageTwo : Fragment() {
    lateinit var binding: OnboardingViewPagerPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerPage2Binding.inflate(layoutInflater)
        return binding.root
    }
}