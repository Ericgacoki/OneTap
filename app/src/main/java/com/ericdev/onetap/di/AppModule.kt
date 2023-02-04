package com.ericdev.onetap.di

import android.app.Application
import androidx.room.Room
import com.ericdev.onetap.core.data.local.database.GoShoppingDatabase
import com.ericdev.onetap.core.data.remote.apiservice.ApiService
import com.ericdev.onetap.feature_onboarding.data.prefs.OnBoardingDataRepository
import com.ericdev.onetap.feature_onboarding.domain.repository.OnBoardingRepository
import com.ericdev.onetap.feature_products.data.local.repository.DataFavoriteProductsRepository
import com.ericdev.onetap.feature_products.data.remote.repository.DataRemoteProductsRepository
import com.ericdev.onetap.util.Constants.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun providesApiService(httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesDataRemoteProductsRepository(apiService: ApiService): DataRemoteProductsRepository {
        return DataRemoteProductsRepository(apiService)
    }

    @Provides
    @Singleton
    fun providesDataFavoriteProductsRepository(database: GoShoppingDatabase): DataFavoriteProductsRepository {
        return DataFavoriteProductsRepository(database)
    }

    @Provides
    @Singleton
    fun providesGoShoppingDatabase(application: Application): GoShoppingDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            GoShoppingDatabase::class.java,
            "go_shopping_database"
        ).fallbackToDestructiveMigration().build()
    }

}
