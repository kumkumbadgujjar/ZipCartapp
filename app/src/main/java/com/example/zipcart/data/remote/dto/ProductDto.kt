package com.example.zipcart.data.remote.dto

data class ProductsResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String
)