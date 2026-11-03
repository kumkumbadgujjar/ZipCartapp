
package com.example.zipcart.frontend.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.data.repository.AuthRepository
import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.usecase.AddToCartUseCase
import com.example.zipcart.domain.usecase.GetProductsUseCase
import com.example.zipcart.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProducts: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeUiState(userName = authRepository.currentUserName())
    )

    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadProducts()
        observeCart()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            when (val result = getProducts()) {

                is Resource.Success -> {
                    val products = result.data

                    _state.update {
                        it.copy(
                            isLoading = false,
                            products = products,
                            categories = listOf("All") +
                                    products.map { product ->
                                        product.category
                                    }.distinct()
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCart().collect { items ->
                _state.update {
                    it.copy(
                        cartCount = items.sumOf { item ->
                            item.quantity
                        }
                    )
                }
            }
        }
    }

    fun onCategorySelected(category: String) {
        _state.update {
            it.copy(selectedCategory = category)
        }
    }

    fun onQueryChange(query: String) {
        _state.update {
            it.copy(query = query)
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product)
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
