package com.ericdev.goshopping.core.data.remote.dto.temp


import com.google.gson.annotations.SerializedName

data class TempProductDtoResultItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("rating")
    val rating: Rating?
)