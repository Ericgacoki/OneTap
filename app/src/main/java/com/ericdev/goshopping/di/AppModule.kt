package com.ericdev.goshopping.di

import android.app.Application
import com.ericdev.goshopping.feature_onboarding.data.prefs.OnBoardingDataRepository
import com.ericdev.goshopping.feature_onboarding.domain.repository.OnBoardingRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesOnBoardingDataRepository(context: Application): OnBoardingRepository {
        return OnBoardingDataRepository(context)
    }
}
