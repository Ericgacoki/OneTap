package com.ericdev.goshopping.feature_products.data.repository

import com.ericdev.goshopping.core.data.remote.dto.CategoryDto
import com.ericdev.goshopping.core.data.remote.dto.ColorDto
import com.ericdev.goshopping.core.data.remote.dto.ProductDto
import com.ericdev.goshopping.core.data.remote.dto.ProductDtoResult
import com.ericdev.goshopping.core.data.remote.dto.temp.Rating
import com.ericdev.goshopping.core.data.remote.dto.temp.TempProductDtoResult
import com.ericdev.goshopping.core.data.remote.dto.temp.TempProductDtoResultItem
import com.ericdev.goshopping.feature_products.domain.repository.RemoteProductsRepository
import com.ericdev.goshopping.util.Resource
import timber.log.Timber

class FakeRemoteProductsRepository : RemoteProductsRepository {
    override suspend fun getAllProducts(): Resource<ProductDtoResult> {
        val fakeProducts = arrayListOf<ProductDto>(
            ProductDto(
                id = "1",
                name = "Toy",
                description = "",
                colorsDto = listOf(ColorDto("Red", "#FF0099")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 4.5,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Kids")
            ), ProductDto(
                id = "2",
                name = "Dior",
                description = "",
                colorsDto = listOf(ColorDto("White", "#FFF")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 5.0,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Men")
            )
        )
        val fakeProductResult = ProductDtoResult(
            products = fakeProducts, pages = 1
        )
        return Resource.Success(data = fakeProductResult)
    }

    override suspend fun getTempProducts(): Resource<TempProductDtoResult> {
        val fakeResult = TempProductDtoResult()

        val fakeTempProducts = arrayListOf(
            TempProductDtoResultItem(
                id = 1,
                title = "Shoe",
                price = 1.0,
                description = "",
                category = "",
                image = "",
                rating = Rating(4.5, 100)
            ),
            TempProductDtoResultItem(
                id = 2,
                title = "Cap",
                price = 1.0,
                description = "",
                category = "",
                image = "",
                rating = Rating(4.5, 100)
            )
        )

        fakeTempProducts.forEach {
            fakeResult.add(it)
        }.also {
            Timber.e("Fake results: ${fakeResult.size}")
        }

        return Resource.Success(data = fakeResult)
    }

    override suspend fun getProductByCategory(categoryId: String): Resource<ProductDtoResult> {
        val fakeProducts = arrayListOf<ProductDto>(
            ProductDto(
                id = "1",
                name = "Toy",
                description = "",
                colorsDto = listOf(ColorDto("Red", "#FF0099")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 4.5,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Kids")
            ), ProductDto(
                id = "2",
                name = "Dior",
                description = "",
                colorsDto = listOf(ColorDto("White", "#FFF")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 5.0,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Men")
            )
        )
        val fakeProductResult = ProductDtoResult(
            products = fakeProducts, pages = 1
        )
        return Resource.Success(data = fakeProductResult)
    }

    override suspend fun searchProduct(params: String): Resource<ProductDtoResult> {
        val fakeProducts = arrayListOf<ProductDto>(
            ProductDto(
                id = "1",
                name = "Toy",
                description = "",
                colorsDto = listOf(ColorDto("Red", "#FF0099")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 4.5,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Kids")
            ), ProductDto(
                id = "2",
                name = "Dior",
                description = "",
                colorsDto = listOf(ColorDto("White", "#FFF")),
                price = 10.0,
                sizes = listOf("42", "44"),
                rating = 5.0,
                images = listOf("url1", "url2"),
                categoryDto = CategoryDto("0", "Men")
            )
        )
        val fakeProductResult = ProductDtoResult(
            products = fakeProducts, pages = 1
        )
        return Resource.Success(data = fakeProductResult)
    }
}
