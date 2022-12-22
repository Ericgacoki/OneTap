package com.ericdev.goshopping.feature_onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdev.goshopping.feature_onboarding.domain.repository.OnBoardingRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: OnBoardingRepository
) : ViewModel() {

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun setOnBoardingFinished(finished: Boolean) {
        viewModelScope.launch {
            repository.setOnBoardingFinished(finished)
        }
    }

    fun getOnBoardingFinished(): Flow<Boolean> = repository.getOnBoardingFinished()
}
