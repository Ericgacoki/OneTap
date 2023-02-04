package com.ericdev.onetap.feature_auth.feature_log_in.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ericdev.onetap.R
import com.ericdev.onetap.databinding.FragmentLogInBinding
import com.ericdev.onetap.feature_auth.feature_log_in.presentation.state.LogInState
import com.ericdev.onetap.feature_auth.feature_log_in.presentation.viewmodel.LogInViewModel
import com.ericdev.validitychecker.ValidityChecker.InValid
import com.ericdev.validitychecker.ValidityChecker.Valid
import com.ericdev.validitychecker.ValidityChecker.Companion.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.e("LOGIN: ON_CREATE_VIEW")
        binding = FragmentLogInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e("LOGIN: ON_VIEW_CREATED")

        binding.tvSignUp.setOnClickListener {
            val action = R.id.action_logInFragment_to_signUpFragment
            findNavController().navigate(action)
        }

        binding.etLogInEmail.addTextChangedListener {
            val newText = binding.etLogInEmail.text.toString().trim()
            viewModel.inputEmail = newText
        }

        binding.etLogInPassword.addTextChangedListener {
            val newText = binding.etLogInPassword.text.toString().trim()
            viewModel.inputPassword = newText
        }

        binding.btnProceedLogIn.setOnClickListener { button ->
            val email = viewModel.inputEmail
            val password = viewModel.inputPassword

            if (isValidEmail(email) is Valid && password.isNotEmpty()) {
                viewModel.logInUser()
                viewModel.logInState.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        LogInState.LOADING -> {
                            button.isEnabled = false
                            Toast.makeText(requireActivity(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        LogInState.SUCCESSFUL -> {
                            Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_SHORT).show()
                            viewModel.logInState.removeObservers(viewLifecycleOwner)
                            findNavController().navigate(R.id.action_logInFragment_to_productsFragment)
                        }
                        LogInState.FAILED -> {
                            button.isEnabled = true
                            Toast.makeText(requireActivity(), "Failed!", Toast.LENGTH_SHORT)
                                .show()
                            viewModel.logInState.removeObservers(viewLifecycleOwner)
                        }
                        else -> {
                            button.isEnabled = true
                            viewModel.logInState.removeObservers(viewLifecycleOwner)
                        }
                    }
                }
            } else if (isValidEmail(email) is InValid) {
                binding.etLogInEmail.error = isValidEmail(email).message
            } else if (password.isEmpty()) {
                binding.etLogInPassword.error = "password is required"
            }
        }
    }
}
