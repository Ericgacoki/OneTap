package com.ericdev.goshopping.feature_splash.presentation.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.FragmentSplashBinding
import com.ericdev.goshopping.feature_onboarding.presentation.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel: OnBoardingViewModel by activityViewModels()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splashGif.let {
            Glide.with(requireActivity()).load(R.drawable.shopping_gif).into(it)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.getCurrentUser()

        viewModel.getOnBoardingFinished().asLiveData()
            .observe(viewLifecycleOwner) { onBoardingFinished ->
                Timber.e("FINISHED_ON_BOARDING: $onBoardingFinished")

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        if (currentUser != null && onBoardingFinished) {
                            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                        } else if (currentUser == null && onBoardingFinished) {
                            findNavController().navigate(R.id.action_splashFragment_to_signUpFragment)
                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                        }
                    }, 4500
                )
            }
    }
}
