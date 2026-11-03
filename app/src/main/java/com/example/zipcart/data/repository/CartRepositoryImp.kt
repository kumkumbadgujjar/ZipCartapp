package com.example.zipcart.data.repository

import com.example.zipcart.data.local.dao.CartDao
import com.example.zipcart.data.mapper.toCartEntity
import com.example.zipcart.data.mapper.toDomain
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val dao: CartDao
) : CartRepository {

    override fun observeCart(): Flow<List<CartItem>> =
        dao.observeCart().map { list -> list.map { it.toDomain() } }

    override suspend fun addToCart(product: Product) {
        val existing = dao.getItem(product.id)
        val newQuantity = (existing?.quantity ?: 0) + 1
        dao.upsert(product.toCartEntity(quantity = newQuantity))
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            dao.delete(productId)
        } else {
            dao.getItem(productId)?.let { dao.upsert(it.copy(quantity = quantity)) }
        }
    }

    override suspend fun clear() = dao.clear()
}