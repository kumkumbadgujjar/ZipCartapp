package com.example.zipcart.domain.usecase


import com.example.zipcart.domain.repository.CartRepository
import javax.inject.Inject

/** A quantity of 0 or less removes the item from the cart. */
class UpdateCartQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) =
        repository.updateQuantity(productId, quantity)
}