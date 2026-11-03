package com.example.zipcart.data.repository

import com.example.zipcart.data.mapper.toDomain
import com.example.zipcart.data.remote.ProductApi
import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.ProductRepository
import com.example.zipcart.util.Resource
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    private var cache: List<Product> = emptyList()

    override suspend fun getProducts(): Resource<List<Product>> = safeCall {
        api.getGroceries().products.map { it.toDomain() }.also { cache = it }
    }

    override suspend fun getProduct(id: Int): Resource<Product> {
        cache.firstOrNull { it.id == id }?.let { return Resource.Success(it) }
        return safeCall { api.getProduct(id).toDomain() }
    }

    private inline fun <T> safeCall(block: () -> T): Resource<T> = try {
        Resource.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        Resource.Error("No internet connection. Please try again.")
    } catch (e: HttpException) {
        Resource.Error("Server error (${e.code()}). Please try again later.")
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Something went wrong")
    }
}