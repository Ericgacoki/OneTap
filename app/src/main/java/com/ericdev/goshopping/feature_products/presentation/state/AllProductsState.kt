package com.ericdev.goshopping.feature_products.presentation.state

import com.ericdev.goshopping.feature_products.domain.model.Product

/**
 *Loading, Success or Error
 * */
sealed interface AllProductsState {
    object Loading : AllProductsState
    data class Success(val data: ArrayList<Product>) : AllProductsState
    data class Error(val message: String) : AllProductsState
}
