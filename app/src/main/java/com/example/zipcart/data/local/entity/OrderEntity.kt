package com.example.zipcart.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val total: Double,
    val itemCount: Int,
    val address: String,
    val timestamp: Long
)