package com.example.zipcart.frontend.screens.cart

import com.example.zipcart.domain.model.CartItem

data class CartUiState(
    val isLoading: Boolean = true,
    val items: List<CartItem> = emptyList()
) {
    val itemCount: Int get() = items.sumOf { it.quantity }
    val itemTotal: Double get() = items.sumOf { it.price * it.quantity }
    val deliveryFee: Double get() = 0.0
    val total: Double get() = itemTotal + deliveryFee
}