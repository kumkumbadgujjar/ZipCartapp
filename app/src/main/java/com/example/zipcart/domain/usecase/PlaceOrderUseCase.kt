package com.example.zipcart.domain.usecase

import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.repository.OrderRepository
import javax.inject.Inject

/** Validates, saves the order, then clears the cart. Returns the new order id. */
class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(items: List<CartItem>, total: Double, address: String): Long {
        require(items.isNotEmpty()) { "Your cart is empty" }
        require(address.isNotBlank()) { "Please enter a delivery address" }

        val orderId = orderRepository.placeOrder(items, total, address.trim())
        cartRepository.clear()
        return orderId
    }
}