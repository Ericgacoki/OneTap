package com.ericdev.goshopping.feature_products.data.repository

import com.ericdev.goshopping.core.data.local.entity.FavoriteProductEntity
import com.ericdev.goshopping.feature_products.domain.repository.FavoriteProductsRepository
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
