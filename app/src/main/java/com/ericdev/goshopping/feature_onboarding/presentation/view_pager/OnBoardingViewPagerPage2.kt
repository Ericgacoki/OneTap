package com.ericdev.goshopping.feature_onboarding.presentation.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ericdev.goshopping.databinding.OnboardingViewPagerPage2Binding

class OnBoardingViewPagerPage2 : Fragment() {
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