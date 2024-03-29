package com.ericdev.onetap.feature_auth.feature_sign_up.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdev.onetap.feature_auth.feature_sign_up.presentation.state.SignUpState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    var inputEmail = ""
    var inputPassword = ""
    var inputConfirmPassword = ""

    private val _signUpState: MutableLiveData<SignUpState> = MutableLiveData(SignUpState.LOADING)
    val signUpState: LiveData<SignUpState> = _signUpState

    fun signUpUser() {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener { task ->
                    _signUpState.value = if (task.isSuccessful) {
                        auth.currentUser?.sendEmailVerification()
                        SignUpState.SUCCESSFUL
                    } else SignUpState.FAILED
                }.addOnCanceledListener {
                    _signUpState.value = SignUpState.FAILED
                }
        }
    }
}
