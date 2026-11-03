package com.example.zipcart.data.remote

import com.example.zipcart.data.remote.dto.ProductDto
import com.example.zipcart.data.remote.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/category/groceries")
    suspend fun getGroceries(): ProductsResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDto
}