package com.ericdev.goshopping.feature_sign_up.domain.repository

import androidx.lifecycle.LiveData
import com.ericdev.goshopping.feature_sign_up.domain.model.SignUpState

interface SignUpRepository {
    suspend fun signUpUser(email: String, password: String): LiveData<SignUpState>
}
