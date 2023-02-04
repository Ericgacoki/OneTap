package com.ericdev.onetap.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
