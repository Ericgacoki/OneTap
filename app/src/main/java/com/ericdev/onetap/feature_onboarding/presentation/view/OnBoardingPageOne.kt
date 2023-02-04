package com.ericdev.onetap.feature_onboarding.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ericdev.onetap.R
import com.ericdev.onetap.databinding.OnboardingViewPagerPage1Binding
import com.ericdev.onetap.feature_onboarding.presentation.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingPageOne : Fragment() {
    private val viewModel: OnBoardingViewModel by activityViewModels()
    lateinit var binding: OnboardingViewPagerPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingViewPagerPage1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSkipPage1.setOnClickListener {
            val route = R.id.action_viewPagerFragment_to_signUpFragment
            viewModel.setOnBoardingFinished(true)
            findNavController().navigate(route)
        }
    }
}
