package com.example.zipcart.frontend.screens.home

import com.example.zipcart.domain.model.Product
data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val userName: String = "",
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val query: String = "",
    val cartCount: Int = 0
) {
    val filteredProducts: List<Product>
        get() = products.filter {
            (selectedCategory == "All" || it.category == selectedCategory) &&
                    it.title.contains(query.trim(), ignoreCase = true)
        }
}