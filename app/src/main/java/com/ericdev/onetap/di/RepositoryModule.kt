package com.ericdev.onetap.di

import com.ericdev.onetap.feature_products.data.local.repository.DataFavoriteProductsRepository
import com.ericdev.onetap.feature_products.data.remote.repository.DataRemoteProductsRepository
import com.ericdev.onetap.feature_products.domain.repository.FavoriteProductsRepository
import com.ericdev.onetap.feature_products.domain.repository.RemoteProductsRepository
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

    @Binds
    @Singleton
    abstract fun bindsDataFavoriteProductRepository(favoriteProductRepository: DataFavoriteProductsRepository): FavoriteProductsRepository
}
