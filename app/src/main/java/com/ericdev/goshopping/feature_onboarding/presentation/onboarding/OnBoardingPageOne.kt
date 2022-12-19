package com.ericdev.goshopping.feature_onboarding.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.OnboardingViewPagerPage1Binding

class OnBoardingPageOne: Fragment() {
    lateinit var binding: OnboardingViewPagerPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerPage1Binding.inflate(layoutInflater)

        binding.btnSkipPage1.setOnClickListener{
            val action = R.id.action_viewPagerFragment_to_signUpFragment
            findNavController().navigate(action)
        }

        return binding.root
    }
}