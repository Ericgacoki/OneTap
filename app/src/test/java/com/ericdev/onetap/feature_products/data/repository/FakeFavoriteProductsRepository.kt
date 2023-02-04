package com.ericdev.onetap.feature_products.data.repository

import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity
import com.ericdev.onetap.feature_products.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.flow.Flow

class FakeFavoriteProductsRepository : FavoriteProductsRepository {
    override suspend fun addToFavorite(entity: FavoriteProductEntity) {}

    override suspend fun removeFromFavorite(entity: FavoriteProductEntity) {}

    /**
     * Already tested in the Favorites Dao
     * */
    override fun getAllFavorite(): Flow<List<FavoriteProductEntity>> {
        TODO("Not yet implemented")
    }
}
