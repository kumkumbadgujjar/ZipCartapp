package com.example.zipcart.data.repository

import com.example.zipcart.data.local.dao.OrderDao
import com.example.zipcart.data.local.entity.OrderEntity
import com.example.zipcart.data.mapper.toDomain
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.model.Order
import com.example.zipcart.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val dao: OrderDao
) : OrderRepository {

    override suspend fun placeOrder(items: List<CartItem>, total: Double, address: String): Long =
        dao.insert(
            OrderEntity(
                total = total,
                itemCount = items.sumOf { it.quantity },
                address = address,
                timestamp = System.currentTimeMillis()
            )
        )

    override fun observeOrders(): Flow<List<Order>> =
        dao.observeOrders().map { list -> list.map { it.toDomain() } }
}