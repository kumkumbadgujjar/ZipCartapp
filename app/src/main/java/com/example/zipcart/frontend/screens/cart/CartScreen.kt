package com.example.zipcart.frontend.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.zipcart.domain.model.CartItem
import com.example.zipcart.frontend.components.CartBottomBar
import com.example.zipcart.frontend.components.EmptyView
import com.example.zipcart.frontend.components.LoadingView
import com.example.zipcart.frontend.components.QuantitySelector
import com.example.zipcart.frontend.components.ZipTopBar
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextPrimary
import com.example.zipcart.ui.theme.ZipTextSecondary
import com.example.zipcart.util.toPrice

import kotlin.collections.isNotEmpty

@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = ZipBackground,
        topBar = { ZipTopBar(title = "Cart", onBack = onBack) },
        bottomBar = {
            if (state.items.isNotEmpty()) {
                Surface(
                    color = ZipSurface,
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    shadowElevation = 8.dp
                ) {
                    Column(Modifier.navigationBarsPadding()) {
                        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
                            Text("Order Summary", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(12.dp))
                            SummaryRow("Item total", state.itemTotal.toPrice())
                            SummaryRow("Delivery", if (state.deliveryFee == 0.0) "Free" else state.deliveryFee.toPrice())
                            SummaryRow("Total", state.total.toPrice(), bold = true)
                        }
                        CartBottomBar(
                            priceText = state.total.toPrice(),
                            actionLabel = "Checkout",
                            onClick = onCheckout
                        )
                    }
                }
            }
        }
    ) { padding ->
        val any = when {
            state.isLoading -> LoadingView(Modifier.padding(padding))
            state.items.isEmpty() -> EmptyView(
                title = "Your cart is empty",
                subtitle = "Add fresh items to get started",
                modifier = Modifier.padding(padding),
                actionLabel = "Start shopping",
                onAction = onBack
            )

            else -> LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "${state.itemCount} items",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ZipTextSecondary
                    )
                }
                items(state.items, key = { it.productId }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrement = { viewModel.increment(item) },
                        onDecrement = { viewModel.decrement(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(item: CartItem, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ZipSurface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    (item.price * item.quantity).toPrice(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            QuantitySelector(item.quantity, onIncrement, onDecrement)
        }
    }
}

/** Also used by CheckoutScreen. */
@Composable
internal fun SummaryRow(label: String, value: String, bold: Boolean = false) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            color = if (bold) ZipTextPrimary else ZipTextSecondary,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            value,
            color = if (bold) ZipTextPrimary else ZipTextSecondary,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }
}