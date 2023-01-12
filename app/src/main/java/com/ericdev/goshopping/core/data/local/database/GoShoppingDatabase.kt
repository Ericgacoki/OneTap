package com.ericdev.goshopping.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericdev.goshopping.core.data.local.dao.FavoriteProductDao
import com.ericdev.goshopping.core.data.local.entity.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1, exportSchema = false)
abstract class GoShoppingDatabase : RoomDatabase() {
    abstract fun favoriteProductsDao(): FavoriteProductDao
}
