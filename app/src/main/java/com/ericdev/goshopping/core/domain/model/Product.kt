package com.ericdev.goshopping.core.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val colors: List<Color>,
    val price: Double,
    val sizes: List<Double>,
    val rating: Double,
    val images: List<String>,
    val category: Category
)
