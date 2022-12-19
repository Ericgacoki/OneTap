package com.ericdev.goshopping.feature_sign_up.presentation

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
import com.ericdev.goshopping.feature_sign_up.util.Validity
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

            if (isValidEmail(email) is Validity.Valid &&
                isValidPassword(password) is Validity.Valid &&
                isValidPassword(confirmPassword) is Validity.Valid &&
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

                        }
                    }
                }

            } else if (isValidEmail(email) is Validity.InValid) {
                binding.etSignUpEmail.error = isValidEmail(email).message
            } else if (isValidPassword(password) is Validity.InValid) {
                binding.etSignUpPassword.error = isValidPassword(password).message
            } else if (isValidPassword(confirmPassword) is Validity.InValid) {
                binding.etSignUpConfirmPassword.error = isValidPassword(confirmPassword).message
            } else {
                binding.etSignUpConfirmPassword.error = "Passwords mismatch"
            }
        }

        return binding.root
    }

    private fun isValidEmail(email: String): Validity {
        val valid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return if (valid) {
            Validity.Valid
        } else {
            if (TextUtils.isEmpty(email))
                Validity.InValid("Email is required")
            else Validity.InValid("Invalid Email")
        }
    }

    private fun isValidPassword(password: String): Validity {
        if (password.length < 8) return Validity.InValid(reason = "Too short")
        if (password.firstOrNull { it.isDigit() } == null) return Validity.InValid("Must have a digit")
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isUpperCase() } == null) return Validity.InValid("Must include uppercase letter")
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isLowerCase() } == null) return Validity.InValid("Must include lowercase letter")
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return Validity.InValid("Must include special character")

        return Validity.Valid
    }
}
