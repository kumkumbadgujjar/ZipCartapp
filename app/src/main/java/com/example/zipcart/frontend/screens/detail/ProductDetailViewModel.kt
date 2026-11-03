package com.example.zipcart.frontend.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.domain.model.Product
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.repository.ProductRepository
import com.example.zipcart.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    private val _state = MutableStateFlow(ProductDetailUiState())
    val state: StateFlow<ProductDetailUiState> = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = ProductDetailUiState(isLoading = true)
            _state.value = when (val result = productRepository.getProduct(productId)) {
                is Resource.Success -> ProductDetailUiState(isLoading = false, product = result.data)
                is Resource.Error -> ProductDetailUiState(isLoading = false, error = result.message)
            }
        }
    }

    fun addToCart() {
        val product = _state.value.product ?: return
        viewModelScope.launch { cartRepository.addToCart(product) }
    }
}