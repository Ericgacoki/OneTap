package com.ericdev.onetap.core.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE
import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = IGNORE)
    suspend fun addToFavorite(entity: FavoriteProductEntity)

    @Delete
    suspend fun removeFromFavorites(entity: FavoriteProductEntity)

    @Query("SELECT * FROM favoriteproductentity")
    fun getAllFavoriteProducts(): Flow<List<FavoriteProductEntity>>
}
