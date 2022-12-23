package com.ericdev.goshopping.feature_products.data.remote.dto.temp


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("count")
    val count: Int?
)