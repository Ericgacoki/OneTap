package com.ericdev.onetap.feature_auth

import com.ericdev.validitychecker.ValidityChecker
import com.google.common.truth.Truth.*
import org.junit.Test

class EmailValidationTests {

    @Test
    fun empty_email_returns_false(){
        val result = ValidityChecker.isValidEmail("")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun short_email_returns_false(){
        val result = ValidityChecker.isValidEmail("a@b")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun incorrect_email_format_returns_false(){
        val result = ValidityChecker.isValidEmail("ab.com")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun correct_email_format_returns_true(){
        val result = ValidityChecker.isValidEmail("test@android.com")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isTrue()
    }
}
