package com.example.zipcart.domain.model

data class Order(
    val id: Long,
    val total: Double,
    val itemCount: Int,
    val address: String,
    val timestamp: Long
)