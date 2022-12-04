package com.ericdev.goshopping.feature_log_in.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.FragmentLogInBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(layoutInflater)
        binding.tvSignUp.setOnClickListener{
            val action = R.id.action_logInFragment_to_signUpFragment
            findNavController().navigate(action)
        }
        return binding.root
    }
}