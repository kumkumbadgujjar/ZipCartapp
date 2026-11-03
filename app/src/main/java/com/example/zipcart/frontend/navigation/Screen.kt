package com.example.zipcart.frontend.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object Home : Screen("home")
    data object Cart : Screen("cart")
    data object Checkout : Screen("checkout")
    data object OrderHistory : Screen("order_history")

    data object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: Int) = "product/$productId"
    }

    data object OrderConfirmation : Screen("order_confirmation/{orderId}") {
        fun createRoute(orderId: Long) = "order_confirmation/$orderId"
    }
}