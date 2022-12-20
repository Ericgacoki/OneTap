package com.ericdev.goshopping.feature_log_in.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.FragmentLogInBinding
import com.ericdev.goshopping.feature_log_in.domain.model.LogInState
import com.ericdev.goshopping.feature_log_in.presentation.viewmodel.LogInViewModel
import com.ericdev.goshopping.util.ValidityChecker.Companion.isValidEmail
import com.ericdev.goshopping.util.ValidityChecker.InValid
import com.ericdev.goshopping.util.ValidityChecker.Valid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(layoutInflater)

        binding.tvSignUp.setOnClickListener {
            val action = R.id.action_logInFragment_to_signUpFragment
            findNavController().navigate(action)
        }

        binding.etLogInEmail.addTextChangedListener {
            val newText = binding.etLogInEmail.text.toString()
            viewModel.inputEmail = newText
        }

        binding.etLogInPassword.addTextChangedListener {
            val newText = binding.etLogInPassword.text.toString()
            viewModel.inputPassword = newText
        }

        binding.btnProceedLogIn.setOnClickListener {
            val email = viewModel.inputEmail
            val password = viewModel.inputPassword

            if (isValidEmail(email) is Valid && password.isNotEmpty()) {
                viewModel.logInUser()
                viewModel.logInState.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        LogInState.LOADING -> {
                            // TODO: Disable button when loading
                            Toast.makeText(requireActivity(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        LogInState.SUCCESSFUL -> {
                            Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                        }
                        LogInState.FAILED -> {
                            Toast.makeText(requireActivity(), "Failed!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {

                        }
                    }
                }
            } else if (isValidEmail(email) is InValid) {
                binding.etLogInEmail.error = isValidEmail(email).message
            } else if (password.isEmpty()) {
                binding.etLogInPassword.error = "password is required"
            }
        }

        return binding.root
    }
}
