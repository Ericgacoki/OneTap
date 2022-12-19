package com.ericdev.goshopping.feature_onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(auth: FirebaseAuth) : ViewModel() {
    val finishedOnBoarding = false
}