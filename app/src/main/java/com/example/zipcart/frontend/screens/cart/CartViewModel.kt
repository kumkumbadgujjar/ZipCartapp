
package com.example.zipcart.frontend.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    cartRepository: CartRepository,
    private val updateQuantity: UpdateCartQuantityUseCase
) : ViewModel() {

    val state: StateFlow<CartUiState> =
        cartRepository.observeCart()
            .map { items ->
                CartUiState(
                    isLoading = false,
                    items = items
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                CartUiState()
            )

    fun increment(item: CartItem) {
        viewModelScope.launch {
            updateQuantity(
                item.productId,
                item.quantity + 1
            )
        }
    }

    fun decrement(item: CartItem) {
        viewModelScope.launch {
            updateQuantity(
                item.productId,
                item.quantity - 1
            )
        }
    }
}
