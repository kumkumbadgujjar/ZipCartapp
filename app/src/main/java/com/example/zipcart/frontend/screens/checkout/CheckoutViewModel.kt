package com.example.zipcart.frontend.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.domain.repository.CartRepository
import com.example.zipcart.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CheckoutUiState(
    val items: List<CartItem> = emptyList(),
    val address: String = "",
    val isPlacing: Boolean = false,
    val error: String? = null,
    val placedOrderId: Long? = null
) {
    val itemTotal: Double get() = items.sumOf { it.price * it.quantity }
    val total: Double get() = itemTotal
}

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutUiState())
    val state: StateFlow<CheckoutUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.observeCart().collect { items ->
                _state.update { it.copy(items = items) }
            }
        }
    }

    fun onAddressChange(address: String) = _state.update { it.copy(address = address, error = null) }

    fun placeOrder() {
        val current = _state.value
        if (current.isPlacing || current.items.isEmpty()) return
        if (current.address.isBlank()) {
            _state.update { it.copy(error = "Please enter a delivery address") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isPlacing = true, error = null) }
            try {
                val orderId = orderRepository.placeOrder(
                    items = current.items,
                    total = current.total,
                    address = current.address.trim()
                )
                cartRepository.clear()
                _state.update { it.copy(isPlacing = false, placedOrderId = orderId) }
            } catch (e: Exception) {
                _state.update { it.copy(isPlacing = false, error = e.message ?: "Could not place order") }
            }
        }
    }
}