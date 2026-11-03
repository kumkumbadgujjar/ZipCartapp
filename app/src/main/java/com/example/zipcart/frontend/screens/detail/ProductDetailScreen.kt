package com.example.zipcart.frontend.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.zipcart.frontend.components.CartBottomBar
import com.example.zipcart.frontend.components.ErrorView
import com.example.zipcart.frontend.components.LoadingView
import com.example.zipcart.frontend.components.ZipTopBar
import com.example.zipcart.ui.theme.*
import com.example.zipcart.util.toPrice
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    onBack: () -> Unit,
    onGoToCart: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val product = state.product

    Scaffold(
        containerColor = ZipBackground,
        topBar = { ZipTopBar(title = "Product Details", onBack = onBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (product != null) {
                CartBottomBar(
                    priceText = product.price.toPrice(),
                    actionLabel = "Add to Cart",
                    onClick = {
                        viewModel.addToCart()
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = "Added to cart",
                                actionLabel = "View cart"
                            )
                            if (result == SnackbarResult.ActionPerformed) onGoToCart()
                        }
                    },
                    modifier = Modifier.background(ZipSurface).navigationBarsPadding()
                )
            }
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.padding(padding))
            state.error != null -> ErrorView(state.error!!, onRetry = viewModel::load, modifier = Modifier.padding(padding))
            product != null -> Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(24.dp)
                )
                Surface(
                    modifier = Modifier.weight(1f),
                    color = ZipSurface,
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                ) {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(product.title, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    product.category.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = ZipTextSecondary
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, null, tint = ZipGreen, modifier = Modifier.size(16.dp))
                                Text(
                                    " ${product.rating}",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                        Text("Description", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            product.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = ZipTextSecondary
                        )

                        Spacer(Modifier.height(20.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            InfoCard(Icons.Filled.Schedule, "Delivery Time", "10 Min", Modifier.weight(1f))
                            InfoCard(Icons.Filled.LocalShipping, "Delivery Type", "Standard", Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ZipBackground)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, null, tint = ZipGreen)
        Column {
            Text(label, style = MaterialTheme.typography.bodySmall, color = ZipTextSecondary)
            Text(value, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        }
    }
}