package com.example.zipcart.domain.usecase

import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.ProductRepository
import com.example.zipcart.util.Resource
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Resource<List<Product>> = repository.getProducts()
}