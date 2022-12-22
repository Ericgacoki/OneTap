package com.ericdev.goshopping.feature_sign_up.presentation.view

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
import com.ericdev.goshopping.databinding.FragmentSignUpBinding
import com.ericdev.goshopping.feature_sign_up.domain.model.SignUpState
import com.ericdev.goshopping.feature_sign_up.presentation.viewmodel.SignUpViewModel
import com.ericdev.goshopping.util.ValidityChecker.Companion.isValidEmail
import com.ericdev.goshopping.util.ValidityChecker.Companion.isValidPassword
import com.ericdev.goshopping.util.ValidityChecker.InValid
import com.ericdev.goshopping.util.ValidityChecker.Valid
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
        binding.tvLogIn.setOnClickListener {
            val action = R.id.action_signUpFragment_to_logInFragment
            findNavController().navigate(action)
        }

        binding.etSignUpEmail.addTextChangedListener {
            val newText = binding.etSignUpEmail.text.toString()
            viewModel.inputEmail = newText
        }
        binding.etSignUpPassword.addTextChangedListener {
            val newText = binding.etSignUpPassword.text.toString()
            viewModel.inputPassword = newText
        }
        binding.etSignUpConfirmPassword.addTextChangedListener {
            val newText = binding.etSignUpConfirmPassword.text.toString()
            viewModel.inputConfirmPassword = newText
        }

        binding.btnProceedSignUp.setOnClickListener {
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
                            Toast.makeText(requireActivity(), "Loading...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        SignUpState.SUCCESSFUL -> {
                            Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                        }
                        SignUpState.FAILED -> {
                            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // null
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
        return binding.root
    }
}