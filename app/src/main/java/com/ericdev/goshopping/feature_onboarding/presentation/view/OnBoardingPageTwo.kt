package com.ericdev.goshopping.feature_onboarding.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.OnboardingViewPagerPage2Binding
import com.ericdev.goshopping.feature_onboarding.presentation.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingPageTwo : Fragment() {
    private val viewModel: OnBoardingViewModel by activityViewModels()
    lateinit var binding: OnboardingViewPagerPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerPage2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSkipPage2.setOnClickListener {
            val route = R.id.action_viewPagerFragment_to_signUpFragment
            viewModel.setOnBoardingFinished(true)
            findNavController().navigate(route)
        }
    }
}
