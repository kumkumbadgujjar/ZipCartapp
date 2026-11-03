package com.example.zipcart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zipcart.data.local.dao.CartDao
import com.example.zipcart.data.local.dao.OrderDao
import com.example.zipcart.data.local.entity.CartItemEntity
import com.example.zipcart.data.local.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ZipCartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}