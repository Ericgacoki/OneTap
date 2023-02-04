package com.ericdev.onetap.core.data.mapper

import com.ericdev.onetap.core.data.remote.dto.CategoryDto
import com.ericdev.onetap.core.data.remote.dto.ColorDto
import com.ericdev.onetap.core.data.remote.dto.ProductDto
import com.ericdev.onetap.core.domain.model.Category
import com.ericdev.onetap.core.domain.model.Color
import com.ericdev.onetap.core.domain.model.Product

object DataMappers {
    internal fun CategoryDto.toCategory(): Category {
        return Category(
            id = id ?: "",
            name = name ?: ""
        )
    }

    internal fun Category.toCategoryDto(): CategoryDto {
        return CategoryDto(
            id = id,
            name = name
        )
    }

    internal fun ColorDto.toColor(): Color {
        return Color(
            name = name ?: "",
            code = code ?: ""
        )
    }

    internal fun Color.toColorDto(): ColorDto {
        return ColorDto(
            name = name,
            code = code
        )
    }

    internal fun ProductDto.toProduct(): Product {
        return Product(
            id = id ?: "",
            name = name ?: "",
            description = description ?: "",
            colors = colorsDto?.map { it.toColor() } ?: emptyList(),
            price = price ?: 0.0,
            sizes = sizes ?: emptyList(),
            rating = rating ?: 0.0,
            images = images ?: emptyList(),
            category = categoryDto?.toCategory() ?: Category("", "")
        )
    }

    internal fun Product.toProductDto(): ProductDto {
        return ProductDto(
            id = id,
            name = name,
            description = description,
            colorsDto = colors.map { it.toColorDto() },
            price = price,
            sizes = sizes,
            rating = rating,
            images = images,
            categoryDto = category.toCategoryDto()
        )
    }
}
