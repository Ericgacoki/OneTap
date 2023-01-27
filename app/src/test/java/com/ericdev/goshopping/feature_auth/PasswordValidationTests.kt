package com.ericdev.goshopping.feature_auth

import com.ericdev.validitychecker.ValidityChecker
import org.junit.Test
import com.google.common.truth.Truth.*


class PasswordValidationTests {

    // Password

    @Test
    fun `empty password returns false`(){
        val result = ValidityChecker.isValidPassword("")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun `short password returns false`(){
        val result = ValidityChecker.isValidPassword("1234")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun `incorrect password format returns false`(){
        val result = ValidityChecker.isValidPassword("1234abcd")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isFalse()
    }

    @Test
    fun `correct password format returns true`(){
        val result = ValidityChecker.isValidPassword("#Testing123")
        val valid = result is ValidityChecker.Valid
        assertThat(valid).isTrue()
    }
}
