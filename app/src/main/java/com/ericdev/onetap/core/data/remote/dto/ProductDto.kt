package com.ericdev.onetap.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("colors")
    val colorsDto: List<ColorDto>?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("sizes")
    val sizes: List<String>?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("category")
    val categoryDto: CategoryDto?
)
