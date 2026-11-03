package com.example.zipcart.frontend.screens.checkout


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zipcart.frontend.components.CartBottomBar
import com.example.zipcart.frontend.components.ZipTextField
import com.example.zipcart.frontend.components.ZipTopBar
import com.example.zipcart.frontend.screens.cart.SummaryRow
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipError
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextSecondary
import com.example.zipcart.util.toPrice


@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onOrderPlaced: (Long) -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.placedOrderId) {
        state.placedOrderId?.let(onOrderPlaced)
    }

    Scaffold(
        containerColor = ZipBackground,
        topBar = { ZipTopBar(title = "Checkout", onBack = onBack) },
        bottomBar = {
            CartBottomBar(
                priceText = state.total.toPrice(),
                actionLabel = "Place Order",
                onClick = viewModel::placeOrder,
                loading = state.isPlacing,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard(title = "Delivery address") {
                ZipTextField(
                    value = state.address,
                    onValueChange = viewModel::onAddressChange,
                    label = "House no, street, city",
                    leadingIcon = Icons.Filled.LocationOn,
                    imeAction = ImeAction.Done
                )
                if (state.error != null) {
                    Text(state.error!!, color = ZipError, style = MaterialTheme.typography.bodySmall)
                }
            }

            SectionCard(title = "Delivery time") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, null, tint = ZipGreen)
                    Spacer(Modifier.width(10.dp))
                    Text("Arrives in about 10 minutes")
                }
            }

            SectionCard(title = "Payment") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = true,
                        onClick = {},
                        colors = RadioButtonDefaults.colors(selectedColor = ZipGreen)
                    )
                    Column {
                        Text("Cash on Delivery", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            "Pay when your order arrives",
                            style = MaterialTheme.typography.bodySmall,
                            color = ZipTextSecondary
                        )
                    }
                }
            }

            SectionCard(title = "Order summary") {
                state.items.forEach {
                    SummaryRow("${it.title.take(24)} × ${it.quantity}", (it.price * it.quantity).toPrice())
                }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                SummaryRow("Delivery", "Free")
                SummaryRow("Total", state.total.toPrice(), bold = true)
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ZipSurface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}