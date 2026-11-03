package com.example.zipcart.domain.usecase

import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(product: Product) = repository.addToCart(product)
}