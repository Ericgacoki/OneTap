package com.ericdev.goshopping.di

import com.ericdev.goshopping.feature_products.data.remote.repository.DataRemoteProductsRepository
import com.ericdev.goshopping.feature_products.domain.repository.RemoteProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteDataProductRepository(remoteProductRepository: DataRemoteProductsRepository): RemoteProductsRepository
}
