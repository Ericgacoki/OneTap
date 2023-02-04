package com.ericdev.onetap.feature_products.domain.repository

import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteProductsRepository {
    suspend fun addToFavorite(entity: FavoriteProductEntity)

    suspend fun removeFromFavorite(entity: FavoriteProductEntity)

    fun getAllFavorite(): Flow<List<FavoriteProductEntity>>
}
