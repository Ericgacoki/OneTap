package com.ericdev.onetap.core.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val colors: List<Color>,
    val price: Double,
    val sizes: List<String>,
    val rating: Double,
    val images: List<String>,
    val category: Category
)
