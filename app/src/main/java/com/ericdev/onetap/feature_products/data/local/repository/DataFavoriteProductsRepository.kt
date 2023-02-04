package com.ericdev.onetap.feature_products.data.local.repository

import com.ericdev.onetap.core.data.local.database.GoShoppingDatabase
import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity
import com.ericdev.onetap.feature_products.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataFavoriteProductsRepository @Inject constructor(private val database: GoShoppingDatabase) :
    FavoriteProductsRepository {
    override suspend fun addToFavorite(entity: FavoriteProductEntity) {
        database.favoriteProductsDao().addToFavorite(entity)
    }

    override suspend fun removeFromFavorite(entity: FavoriteProductEntity) {
        database.favoriteProductsDao().removeFromFavorites(entity)
    }

    override fun getAllFavorite(): Flow<List<FavoriteProductEntity>> {
        return database.favoriteProductsDao().getAllFavoriteProducts()
    }
}
