package com.ericdev.onetap.feature_auth.feature_sign_up.presentation.view

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
import com.ericdev.onetap.databinding.FragmentSignUpBinding
import com.ericdev.onetap.feature_auth.feature_sign_up.presentation.state.SignUpState
import com.ericdev.onetap.feature_auth.feature_sign_up.presentation.viewmodel.SignUpViewModel
import com.ericdev.validitychecker.ValidityChecker.Valid
import com.ericdev.validitychecker.ValidityChecker.InValid
import com.ericdev.validitychecker.ValidityChecker.Companion.isValidEmail
import com.ericdev.validitychecker.ValidityChecker.Companion.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogIn.setOnClickListener {
            val action = R.id.action_signUpFragment_to_logInFragment
            findNavController().navigate(action)
        }

        binding.etSignUpEmail.addTextChangedListener {
            val newText = binding.etSignUpEmail.text.toString().trim()
            viewModel.inputEmail = newText
        }
        binding.etSignUpPassword.addTextChangedListener {
            val newText = binding.etSignUpPassword.text.toString().trim()
            viewModel.inputPassword = newText
        }
        binding.etSignUpConfirmPassword.addTextChangedListener {
            val newText = binding.etSignUpConfirmPassword.text.toString().trim()
            viewModel.inputConfirmPassword = newText
        }

        binding.btnProceedSignUp.setOnClickListener { button ->
            val email = viewModel.inputEmail
            val password = viewModel.inputPassword
            val confirmPassword = viewModel.inputConfirmPassword

            if (isValidEmail(email) is Valid &&
                isValidPassword(password) is Valid &&
                isValidPassword(confirmPassword) is Valid &&
                password == confirmPassword
            ) {
                viewModel.signUpUser()
                viewModel.signUpState.observe(viewLifecycleOwner) {
                    when (it) {
                        SignUpState.LOADING -> {
                            button.isEnabled = false
                            Toast.makeText(requireActivity(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        SignUpState.SUCCESSFUL -> {
                            Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_SHORT).show()
                            viewModel.signUpState.removeObservers(viewLifecycleOwner)
                            findNavController().navigate(R.id.action_signUpFragment_to_productsFragment)
                        }
                        SignUpState.FAILED -> {
                            button.isEnabled = true
                            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
                            viewModel.signUpState.removeObservers(viewLifecycleOwner)
                        }
                        else -> {
                            button.isEnabled = true
                            viewModel.signUpState.removeObservers(viewLifecycleOwner)
                        }
                    }
                }
            } else if (isValidEmail(email) is InValid) {
                binding.etSignUpEmail.error = isValidEmail(email).message
            } else if (isValidPassword(password) is InValid) {
                binding.etSignUpPassword.error = isValidPassword(password).message
            } else if (isValidPassword(confirmPassword) is InValid) {
                binding.etSignUpConfirmPassword.error = isValidPassword(confirmPassword).message
            } else {
                binding.etSignUpConfirmPassword.error = "Passwords mismatch"
            }
        }
    }
}
