package com.ericdev.goshopping.feature_products.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ColorDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?
)
