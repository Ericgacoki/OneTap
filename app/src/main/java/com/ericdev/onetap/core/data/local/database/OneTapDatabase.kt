package com.ericdev.onetap.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericdev.onetap.core.data.local.dao.FavoriteProductDao
import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1, exportSchema = false)
abstract class OneTapDatabase : RoomDatabase() {
    abstract fun favoriteProductsDao(): FavoriteProductDao
}
