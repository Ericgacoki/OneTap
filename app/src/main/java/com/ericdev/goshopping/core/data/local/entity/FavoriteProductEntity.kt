package com.ericdev.goshopping.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteproductentity")
data class FavoriteProductEntity(
    @PrimaryKey(autoGenerate = false)
    val productId: Int
)
