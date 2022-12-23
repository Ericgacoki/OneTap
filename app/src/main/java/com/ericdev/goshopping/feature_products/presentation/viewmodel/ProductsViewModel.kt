package com.ericdev.goshopping.feature_products.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdev.goshopping.feature_products.data.mapper.DataMappers.toProduct
import com.ericdev.goshopping.feature_products.data.remote.dto.temp.TempProductDtoResultItem
import com.ericdev.goshopping.feature_products.domain.model.Product
import com.ericdev.goshopping.feature_products.domain.repository.RemoteProductsRepository
import com.ericdev.goshopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(val repository: RemoteProductsRepository) :
    ViewModel() {

    private var _tempProductsState: MutableLiveData<Resource<List<TempProductDtoResultItem>?>> =
        MutableLiveData(Resource.Loading())
    val tempProductsState: LiveData<Resource<List<TempProductDtoResultItem>?>> = _tempProductsState

    init {
        // getAllProducts()
        getTempProducts()
    }

    fun getTempProducts() {
        _tempProductsState.value = Resource.Loading()

        viewModelScope.launch {
            when (val result = repository.getTempProducts()) {
                is Resource.Loading -> {
                    _tempProductsState.value = Resource.Loading() // redundant setter
                }
                is Resource.Error -> {
                    _tempProductsState.value = Resource.Error(null, result.message!!)
                }
                is Resource.Success -> {
                    _tempProductsState.value =
                        Resource.Success(data = result.data ?: emptyList())
                }
            }
        }
    }


    private var _allProductsState: MutableLiveData<Resource<List<Product>?>> =
        MutableLiveData(Resource.Loading())
    val allProductsState: LiveData<Resource<List<Product>?>> = _allProductsState


    // TODO("make this public after implementing the new end point")
    private fun getAllProducts() {
        viewModelScope.launch {
            when (val result = repository.getAllProducts()) {
                is Resource.Loading -> {
                    _allProductsState.value = Resource.Loading()
                }
                is Resource.Error -> {
                    _allProductsState.value = Resource.Error(null, result.message!!)
                }
                is Resource.Success -> {
                    _allProductsState.value =
                        Resource.Success(data = result.data?.products?.map { it.toProduct() }
                            ?: emptyList())
                }
            }
        }
    }
}
