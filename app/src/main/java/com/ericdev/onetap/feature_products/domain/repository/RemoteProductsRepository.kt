package com.ericdev.onetap.feature_products.domain.repository

import com.ericdev.onetap.core.data.remote.dto.ProductDtoResult
import com.ericdev.onetap.core.data.remote.dto.temp.TempProductDtoResult
import com.ericdev.onetap.util.Resource

interface RemoteProductsRepository {
    suspend fun getAllProducts(): Resource<ProductDtoResult>

    suspend fun getTempProducts(): Resource<TempProductDtoResult>

    suspend fun getProductByCategory(categoryId: String): Resource<ProductDtoResult>

    suspend fun searchProduct(params: String): Resource<ProductDtoResult>
}
