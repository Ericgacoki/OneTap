package com.ericdev.onetap.feature_products.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ericdev.onetap.feature_products.data.repository.FakeRemoteProductsRepository
import com.ericdev.onetap.util.Resource
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = ProductsViewModel(FakeRemoteProductsRepository(), null, null)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get temporary products`() = runTest {
        // initial value
        val isLoading = viewModel.tempProductsStateFlow.value is Resource.Loading
        assertEquals(true, isLoading)

       // simulate api delay
        withContext(Dispatchers.Default) {
            delay(1000)
        }
        val tempProducts = viewModel.tempProductsStateFlow.value

        val isSuccess = tempProducts is Resource.Success
        assertEquals(true, isSuccess)
        assertThat(tempProducts.data).isNotEmpty()
    }

    @Test
    fun `get all products`() = runTest {
        // initial value
        val isLoading = viewModel.allProducts.value is Resource.Loading
        assertEquals(true, isLoading)

        viewModel.getAllProducts()
        withContext(Dispatchers.Default) {
            delay(100)
        }

        val products: Resource<*> = viewModel.allProducts.value

        val isSuccess = products is Resource.Success
        assertEquals(true, isSuccess)
    }
}
