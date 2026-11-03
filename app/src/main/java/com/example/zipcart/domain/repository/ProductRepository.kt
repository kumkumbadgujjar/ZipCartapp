package com.example.zipcart.domain.repository

import com.example.zipcart.domain.model.Product
import com.example.zipcart.util.Resource

interface ProductRepository {
    suspend fun getProducts(): Resource<List<Product>>
    suspend fun getProduct(id: Int): Resource<Product>
}