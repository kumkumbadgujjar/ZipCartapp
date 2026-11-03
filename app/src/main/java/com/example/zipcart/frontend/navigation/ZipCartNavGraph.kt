package com.example.zipcart.frontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zipcart.frontend.screens.auth.LoginScreen
import com.example.zipcart.frontend.screens.auth.SignUpScreen
import com.example.zipcart.frontend.screens.cart.CartScreen
import com.example.zipcart.frontend.screens.checkout.CheckoutScreen
import com.example.zipcart.frontend.screens.detail.ProductDetailScreen
import com.example.zipcart.frontend.screens.home.HomeScreen
import com.example.zipcart.frontend.screens.onboarding.OnboardingScreen
import com.example.zipcart.frontend.screens.orders.OrderConfirmationScreen
import com.example.zipcart.frontend.screens.orders.OrderHistoryScreen

@Composable
fun ZipCartNavGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    val goToLogin: () -> Unit = {
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Onboarding.route) { inclusive = true }
        }
    }
    val goToHome: () -> Unit = {
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen(onGetStarted = goToLogin, onSkip = goToLogin)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = goToHome,
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = goToHome,
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { navController.navigate(Screen.ProductDetail.createRoute(it)) },
                onCartClick = { navController.navigate(Screen.Cart.route) },
                onOrdersClick = { navController.navigate(Screen.OrderHistory.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductDetailScreen(
                onBack = { navController.popBackStack() },
                onGoToCart = { navController.navigate(Screen.Cart.route) }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBack = { navController.popBackStack() },
                onCheckout = { navController.navigate(Screen.Checkout.route) }
            )
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBack = { navController.popBackStack() },
                onOrderPlaced = { orderId ->
                    navController.navigate(Screen.OrderConfirmation.createRoute(orderId)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(
            route = Screen.OrderConfirmation.route,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) { entry ->
            OrderConfirmationScreen(
                orderId = entry.arguments?.getLong("orderId") ?: 0L,
                onViewOrders = {
                    navController.navigate(Screen.OrderHistory.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onContinueShopping = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(onBack = { navController.popBackStack() })
        }
    }
}