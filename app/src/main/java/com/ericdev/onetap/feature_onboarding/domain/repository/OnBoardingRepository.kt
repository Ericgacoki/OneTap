package com.ericdev.onetap.feature_onboarding.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun setOnBoardingFinished(finished: Boolean)

    fun getOnBoardingFinished(): Flow<Boolean>
}