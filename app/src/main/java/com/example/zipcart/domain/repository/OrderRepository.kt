package com.example.zipcart.domain.repository

import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun placeOrder(items: List<CartItem>, total: Double, address: String): Long
    fun observeOrders(): Flow<List<Order>>
}