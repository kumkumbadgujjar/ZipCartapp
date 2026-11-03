
package com.example.zipcart.frontend.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.domain.model.Order
import com.example.zipcart.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class OrdersUiState(
    val isLoading: Boolean = true,
    val orders: List<Order> = emptyList()
)

@HiltViewModel
class OrdersViewModel @Inject constructor(
    orderRepository: OrderRepository
) : ViewModel() {

    val state: StateFlow<OrdersUiState> =
        orderRepository.observeOrders()
            .map { orders ->
                OrdersUiState(
                    isLoading = false,
                    orders = orders
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                OrdersUiState()
            )
}
