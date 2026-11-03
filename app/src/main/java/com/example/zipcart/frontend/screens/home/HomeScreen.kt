package com.example.zipcart.frontend.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zipcart.frontend.components.EmptyView
import com.example.zipcart.frontend.components.ErrorView
import com.example.zipcart.frontend.components.LoadingView
import com.example.zipcart.frontend.components.NavTab
import com.example.zipcart.frontend.components.ProductCard
import com.example.zipcart.frontend.components.ZipBottomNav
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipError
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextSecondary
import com.example.zipcart.frontend.components.CategoryChip


@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Do you want to log out of ZipCart?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    viewModel.logout()
                    onLogout()
                }) { Text("Logout", color = ZipError) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        containerColor = ZipBackground,
        bottomBar = {
            ZipBottomNav(
                selected = NavTab.Home,
                cartCount = state.cartCount,
                onHome = {},
                onOrders = onOrdersClick,
                onCart = onCartClick,
                onProfile = { showLogoutDialog = true }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            // Header
            Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    "Hi, ${state.userName.ifBlank { "there" }} 👋",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Bolt, null, tint = ZipGreen, modifier = Modifier.size(16.dp))
                    Text(
                        "Delivery in 10 minutes",
                        style = MaterialTheme.typography.bodySmall,
                        color = ZipTextSecondary
                    )
                }
            }

            when {
                state.isLoading -> LoadingView()
                state.error != null -> ErrorView(state.error!!, onRetry = viewModel::loadProducts)
                else -> {
                    // Categories
                    Text(
                        "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.categories) { category ->
                            CategoryChip(
                                label = category,
                                selected = category == state.selectedCategory,
                                onClick = { viewModel.onCategorySelected(category) }
                            )
                        }
                    }

                    // Search
                    OutlinedTextField(
                        value = state.query,
                        onValueChange = viewModel::onQueryChange,
                        placeholder = { Text("Search groceries") },
                        leadingIcon = { Icon(Icons.Filled.Search, null) },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ZipGreen,
                            cursorColor = ZipGreen,
                            focusedContainerColor = ZipSurface,
                            unfocusedContainerColor = ZipSurface,
                            unfocusedBorderColor = ZipSurface
                        )
                    )

                    // Products
                    val products = state.filteredProducts
                    if (products.isEmpty()) {
                        EmptyView(
                            title = "No products found",
                            subtitle = "Try a different search or category"
                        )               } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = 24.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                products,
                                key = { it.id }
                            ) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = {
                                        onProductClick(product.id)
                                    },
                                    onAddClick = {
                                        viewModel.addToCart(product)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}