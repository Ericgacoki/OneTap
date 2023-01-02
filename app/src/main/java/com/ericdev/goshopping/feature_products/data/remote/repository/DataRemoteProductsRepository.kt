package com.ericdev.goshopping.feature_products.data.remote.repository

import com.ericdev.goshopping.feature_products.data.remote.apiservice.ApiService
import com.ericdev.goshopping.feature_products.data.remote.dto.ProductDtoResult
import com.ericdev.goshopping.feature_products.data.remote.dto.temp.TempProductDtoResult
import com.ericdev.goshopping.feature_products.domain.repository.RemoteProductsRepository
import com.ericdev.goshopping.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DataRemoteProductsRepository @Inject constructor(
    private val apiService: ApiService
) : RemoteProductsRepository {

    override suspend fun getTempProducts(): Resource<TempProductDtoResult> {
        return try {
            Resource.Success(apiService.getTempProducts())
        } catch (e: IOException) {
            Resource.Error(null, message = e.localizedMessage ?: "Timeout!")
        } catch (e: HttpException) {
            Resource.Error(null, message = e.localizedMessage ?: "Network Error!")
        }
    }

    override suspend fun getAllProducts(): Resource<ProductDtoResult> {
        return try {
            Resource.Success(apiService.getAllProducts())
        } catch (e: IOException) {
            Resource.Error(null, message = e.localizedMessage ?: "Timeout!")
        } catch (e: HttpException) {
            Resource.Error(null, message = e.localizedMessage ?: "Network Error!")
        }
    }


    override suspend fun getProductByCategory(categoryId: String): Resource<ProductDtoResult> {
        return try {
            Resource.Success(apiService.getProductsByCategory(categoryId))
        } catch (e: IOException) {
            Resource.Error(null, message = e.localizedMessage ?: "Timeout!")
        } catch (e: HttpException) {
            Resource.Error(null, message = e.localizedMessage ?: "Network Error!")
        }
    }

    override suspend fun searchProduct(params: String): Resource<ProductDtoResult> {
        return try {
            Resource.Success(apiService.searchProduct(params))
        } catch (e: IOException) {
            Resource.Error(null, message = e.localizedMessage ?: "Timeout!")
        } catch (e: HttpException) {
            Resource.Error(null, message = e.localizedMessage ?: "Network Error!")
        }
    }
}
