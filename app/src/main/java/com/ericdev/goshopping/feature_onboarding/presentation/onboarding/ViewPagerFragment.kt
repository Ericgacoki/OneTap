package com.ericdev.goshopping.feature_onboarding.presentation.onboarding

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
    lateinit var binding: FragmentViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewpagerBinding.inflate(layoutInflater)

        /*
        val currentUser = auth.currentUser
        val viewModel: ViewPagerViewModel by viewModels()
        val finishedOnBoarding = viewModel.finishedOnBoarding

        if (currentUser != null && finishedOnBoarding) {
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
        } else if (currentUser == null && finishedOnBoarding) {
            findNavController().navigate(R.id.action_viewPagerFragment_to_signUpFragment)
        } else {
            // Do Nothing
        } */

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

        TabLayoutMediator(pagerIndicator, viewPager) { tab, position ->

        }.attach()

        return binding.root
    }

    /*  override fun onStart() {
          super.onStart()

          val currentUser = auth?.currentUser
          val viewModel: ViewPagerViewModel by viewModels()
          val finishedOnBoarding = viewModel.finishedOnBoarding

          if (currentUser != null && finishedOnBoarding) {
              findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
          } else if (currentUser == null && finishedOnBoarding) {
              findNavController().navigate(R.id.action_viewPagerFragment_to_signUpFragment)
          } else {
              // Do Nothing
          }
      }*/
}
