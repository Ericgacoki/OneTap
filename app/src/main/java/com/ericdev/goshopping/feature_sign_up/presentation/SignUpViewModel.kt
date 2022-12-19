package com.ericdev.goshopping.feature_sign_up.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdev.goshopping.feature_sign_up.domain.model.SignUpState
import com.ericdev.goshopping.feature_sign_up.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val repository: SignUpRepository
) : ViewModel() {

    var inputEmail = ""
    var inputPassword = ""
    var inputConfirmPassword = ""

    private val _signUpState = MutableLiveData<SignUpState>(SignUpState.LOADING)
    val signUpState get() = _signUpState

    fun signUpUser() {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener() { task ->
                    _signUpState.value = if (task.isSuccessful) {
                        auth.currentUser?.sendEmailVerification()
                        SignUpState.SUCCESSFUL
                    } else {
                        SignUpState.FAILED
                    }
                }
        }
    }

    fun checkUser(): FirebaseUser? {
        return auth.currentUser
    }
}
