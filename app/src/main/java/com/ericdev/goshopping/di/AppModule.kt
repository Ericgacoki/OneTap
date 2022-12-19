package com.ericdev.goshopping.di

import android.app.Application
import com.ericdev.goshopping.feature_sign_up.data.repository.SignUpDataRepository
import com.ericdev.goshopping.feature_sign_up.domain.repository.SignUpRepository
import com.google.firebase.FirebaseApp
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
    fun providesSignUpDataRepository(auth: FirebaseAuth): SignUpRepository {
        return SignUpDataRepository(auth)
    }
}
