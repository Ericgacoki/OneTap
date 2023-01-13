package com.ericdev.goshopping

import com.ericdev.validitychecker.ValidityChecker
import com.google.common.truth.Truth
import org.junit.Test

class EmailValidationTests {

    @Test
    fun emptyEmailReturnsFalse(){
        val result = ValidityChecker.isValidEmail("")
        val valid = result is ValidityChecker.Valid
        Truth.assertThat(valid).isFalse()
    }

    @Test
    fun shortEmailReturnsFalse(){
        val result = ValidityChecker.isValidEmail("a@b")
        val valid = result is ValidityChecker.Valid
        Truth.assertThat(valid).isFalse()
    }

    @Test
    fun incorrectEmailFormatReturnsFalse(){
        val result = ValidityChecker.isValidEmail("ab.com")
        val valid = result is ValidityChecker.Valid
        Truth.assertThat(valid).isFalse()
    }

    @Test
    fun correctEmailFormatReturnsTrue(){
        val result = ValidityChecker.isValidEmail("test@android.com")
        val valid = result is ValidityChecker.Valid
        Truth.assertThat(valid).isTrue()
    }
}
