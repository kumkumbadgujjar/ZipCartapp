package com.example.zipcart.di

import android.content.Context
import androidx.room.Room
import com.example.zipcart.data.local.ZipCartDatabase
import com.example.zipcart.data.local.dao.CartDao
import com.example.zipcart.data.local.dao.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ZipCartDatabase =
        Room.databaseBuilder(context, ZipCartDatabase::class.java, "zipcart.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun provideCartDao(db: ZipCartDatabase): CartDao = db.cartDao()

    @Provides
    fun provideOrderDao(db: ZipCartDatabase): OrderDao = db.orderDao()
}