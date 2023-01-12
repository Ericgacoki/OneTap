package com.ericdev.goshopping.core.data.remote.apiservice

import com.ericdev.goshopping.core.data.remote.dto.ProductDtoResult
import com.ericdev.goshopping.core.data.remote.dto.temp.TempProductDtoResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products/")
    suspend fun getTempProducts(): TempProductDtoResult

    @GET("products/")
    suspend fun getAllProducts(): ProductDtoResult

    @GET("products/")
    suspend fun getProductsByCategory(
        @Query("category_id") categoryId: String
    ): ProductDtoResult

    @GET("products/search/")
    suspend fun searchProduct(
        @Query("params") params: String
    ): ProductDtoResult
}
