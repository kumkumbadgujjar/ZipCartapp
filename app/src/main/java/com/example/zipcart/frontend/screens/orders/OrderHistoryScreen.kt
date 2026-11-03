
package com.example.zipcart.frontend.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zipcart.domain.model.Order
import com.example.zipcart.frontend.components.EmptyView
import com.example.zipcart.frontend.components.LoadingView
import com.example.zipcart.frontend.components.ZipTopBar
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipGreenDark
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextSecondary
import com.example.zipcart.util.toPrice
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderHistoryScreen(
    onBack: () -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = ZipBackground,
        topBar = {
            ZipTopBar(
                title = "My Orders",
                onBack = onBack
            )
        }
    ) { padding ->

        when {
            state.isLoading -> {
                LoadingView(Modifier.padding(padding))
            }

            state.orders.isEmpty() -> {
                EmptyView(
                    title = "No orders yet",
                    subtitle = "Your placed orders will show up here",
                    modifier = Modifier.padding(padding),
                    actionLabel = "Start shopping",
                    onAction = onBack
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.orders,
                        key = { order -> order.id }
                    ) { order ->
                        OrderCard(order)
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderCard(order: Order) {
    val date = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    ).format(Date(order.timestamp))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = ZipSurface
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Order #${order.id}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    order.total.toPrice(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ZipGreenDark
                )
            }

            Text(
                date,
                style = MaterialTheme.typography.bodySmall,
                color = ZipTextSecondary
            )

            Text(
                "${order.itemCount} items",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                order.address,
                style = MaterialTheme.typography.bodySmall,
                color = ZipTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
