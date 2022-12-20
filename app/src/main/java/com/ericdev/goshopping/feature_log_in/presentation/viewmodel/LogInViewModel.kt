package com.ericdev.goshopping.feature_log_in.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ericdev.goshopping.feature_log_in.domain.model.LogInState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private val _logInState: MutableLiveData<LogInState> = MutableLiveData(LogInState.LOADING)
    val logInState: LiveData<LogInState> = _logInState

    var inputEmail = ""
    var inputPassword = ""

    fun logInUser() {
        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener {
                _logInState.value = if (it.isSuccessful)
                    LogInState.SUCCESSFUL else LogInState.FAILED
            }.addOnCanceledListener {
                _logInState.value = LogInState.FAILED
            }
    }
}
