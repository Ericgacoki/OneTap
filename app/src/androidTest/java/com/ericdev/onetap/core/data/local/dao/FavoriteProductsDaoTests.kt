package com.ericdev.onetap.core.data.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ericdev.onetap.core.data.local.database.GoShoppingDatabase
import com.ericdev.onetap.core.data.local.entity.FavoriteProductEntity
import com.ericdev.onetap.util.LivedataTestUtil.getValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteProductsDaoTests {
    private lateinit var favoritesDao: FavoriteProductDao
    private lateinit var database: GoShoppingDatabase

    @get:Rule(order = 0)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(context, GoShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        favoritesDao = database.favoriteProductsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun empty_table_returns_zero_items(): Unit = runBlocking {
        val favoriteProducts = favoritesDao.getAllFavoriteProducts().asLiveData()
        val emptyData = getValue(favoriteProducts)?.isEmpty() ?: false
        assertThat(emptyData).isTrue()
    }

    @Test
    fun non_empty_table_returns_n_items(): Unit = runBlocking {
        val entity = FavoriteProductEntity(1)
        favoritesDao.addToFavorite(entity)

        val favoriteProducts = favoritesDao.getAllFavoriteProducts().asLiveData()
        val notEmptyData = getValue(favoriteProducts)?.isNotEmpty() ?: false
        assertThat(notEmptyData).isTrue()
    }

    @Test
    fun given_item_is_in_favorites(): Unit = runBlocking {
        val entity = FavoriteProductEntity(999)
        favoritesDao.addToFavorite(entity)

        val favoriteProducts = favoritesDao.getAllFavoriteProducts().asLiveData()
        val itemInFav = getValue(favoriteProducts)?.contains(entity) ?: false
        assertThat(itemInFav).isTrue()
    }

    @Test
    fun deleted_item_is_not_in_favorite(): Unit = runBlocking {
        val entity1 = FavoriteProductEntity(1)
        val entity2 = FavoriteProductEntity(2)

        favoritesDao.addToFavorite(entity1)
        favoritesDao.addToFavorite(entity2) // make sure list is not empty

        val initialFavoriteProducts = favoritesDao.getAllFavoriteProducts().asLiveData()
        val inFav = getValue(initialFavoriteProducts)?.contains(entity1) ?: false

        favoritesDao.removeFromFavorites(entity1) // delete the target item
        val currentFavoriteProducts = favoritesDao.getAllFavoriteProducts().asLiveData()
        val deleted = getValue(currentFavoriteProducts)?.contains(entity1)?.not() ?: false

        assertThat(inFav && deleted).isTrue()
    }
}
