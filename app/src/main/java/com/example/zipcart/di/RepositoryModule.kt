package com.example.zipcart.di

import com.example.zipcart.data.repository.CartRepositoryImpl
import com.example.zipcart.data.repository.OrderRepositoryImpl
import com.example.zipcart.data.repository.ProductRepositoryImpl
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.repository.OrderRepository
import com.example.zipcart.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository
}