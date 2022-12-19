package com.ericdev.goshopping.feature_sign_up.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ericdev.goshopping.feature_sign_up.domain.model.SignUpState
import com.ericdev.goshopping.feature_sign_up.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpDataRepository @Inject constructor(private val auth: FirebaseAuth) : SignUpRepository {
    override suspend fun signUpUser(email: String, password: String): LiveData<SignUpState> {
        val signUpState: MutableLiveData<SignUpState> = MutableLiveData(SignUpState.LOADING)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                signUpState.value = if (task.isSuccessful) {
                    SignUpState.SUCCESSFUL
                } else {
                    SignUpState.FAILED
                }
            }
        return signUpState
    }
}
