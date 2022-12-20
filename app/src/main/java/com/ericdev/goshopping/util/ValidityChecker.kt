package com.ericdev.goshopping.util

import android.text.TextUtils
import android.util.Patterns

sealed class ValidityChecker(val message: String = "") {
    object Valid : ValidityChecker()
    data class InValid(val reason: String) : ValidityChecker(reason)

    companion object {
        internal fun isValidEmail(email: String): ValidityChecker {
            val valid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            return if (valid) {
                Valid
            } else {
                if (TextUtils.isEmpty(email))
                    InValid("Email is required")
                else InValid("Invalid Email")
            }
        }

        internal fun isValidPassword(password: String): ValidityChecker {
            if (password.length < 8) return InValid(reason = "Too short")
            if (password.firstOrNull { it.isDigit() } == null) return InValid("Must have a digit")
            if (password.filter { it.isLetter() }
                    .firstOrNull { it.isUpperCase() } == null) return InValid("Must include uppercase letter")
            if (password.filter { it.isLetter() }
                    .firstOrNull { it.isLowerCase() } == null) return InValid("Must include lowercase letter")
            if (password.firstOrNull { !it.isLetterOrDigit() } == null) return InValid("Must include special character")

            return Valid
        }
    }
}
