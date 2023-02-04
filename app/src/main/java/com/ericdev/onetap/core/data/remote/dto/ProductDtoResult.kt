package com.ericdev.onetap.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDtoResult(
    @SerializedName("products")
    val products: ArrayList<ProductDto>?,
    @SerializedName("pages")
    val pages: Int?
)
