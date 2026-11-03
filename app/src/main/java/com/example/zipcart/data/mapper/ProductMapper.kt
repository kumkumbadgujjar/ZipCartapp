package com.example.zipcart.data.mapper

import com.example.zipcart.data.local.entity.CartItemEntity
import com.example.zipcart.data.local.entity.OrderEntity
import com.example.zipcart.data.remote.dto.ProductDto
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.model.Order
import com.example.zipcart.domain.model.Product


fun ProductDto.toDomain() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = categoryFor(title),
    imageUrl = thumbnail,
    rating = rating
)

fun Product.toCartEntity(quantity: Int = 1) = CartItemEntity(
    productId = id,
    title = title,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity
)

fun CartItemEntity.toDomain() = CartItem(
    productId = productId,
    title = title,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity
)

fun OrderEntity.toDomain() = Order(
    id = id,
    total = total,
    itemCount = itemCount,
    address = address,
    timestamp = timestamp
)

/**
 * The API only has one "groceries" category, so we split it into
 * friendlier categories by keyword to get useful category chips.
 */
private fun categoryFor(title: String): String {
    val t = title.lowercase()
    return when {
        listOf("apple", "kiwi", "lemon", "mulberry", "strawberry").any { it in t } -> "Fruits"
        listOf("cucumber", "pepper", "chili", "potato", "onion").any { it in t } -> "Vegetables"
        listOf("milk", "egg", "ice cream").any { it in t } -> "Dairy"
        listOf("juice", "water", "soft drink", "coffee").any { it in t } -> "Drinks"
        listOf("beef", "chicken", "fish").any { it in t } -> "Meat"
        listOf("rice", "oil", "honey", "protein").any { it in t } -> "Pantry"
        else -> "Essentials"
    }
}