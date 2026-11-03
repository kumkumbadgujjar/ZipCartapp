package com.example.zipcart.domain.repository

import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product)                    // +1 if already in cart
    suspend fun updateQuantity(productId: Int, quantity: Int)  // 0 or less removes the item
    suspend fun clear()
}