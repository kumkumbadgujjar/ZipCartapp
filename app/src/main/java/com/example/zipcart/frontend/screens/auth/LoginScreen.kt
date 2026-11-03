package com.example.zipcart.frontend.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zipcart.frontend.components.ZipTextField
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipError
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipGreenDark
import com.example.zipcart.ui.theme.ZipTextSecondary


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) {
            viewModel.reset()
            onLoginSuccess()
        }
    }

    AuthLayout(title = "Welcome back", subtitle = "Login to continue shopping") {
        ZipTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            leadingIcon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(12.dp))
        ZipTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            leadingIcon = Icons.Filled.Lock,
            isPassword = true,
            imeAction = ImeAction.Done
        )
        AuthError(state.error)
        Spacer(Modifier.height(20.dp))
        AuthButton("Login", state.isLoading) { viewModel.login(email, password) }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("New to ZipCart?", color = ZipTextSecondary)
            TextButton(onClick = onSignUpClick) {
                Text("Create account", color = ZipGreenDark)
            }
        }
    }
}

/* ---------- Shared auth UI (also used by SignUpScreen) ---------- */

@Composable
internal fun AuthLayout(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ZipBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(32.dp))
        Box(
            Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(ZipGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ShoppingCart, null, tint = Color.White, modifier = Modifier.size(30.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, color = ZipTextSecondary)
        Spacer(Modifier.height(32.dp))
        content()
    }
}

@Composable
internal fun AuthError(message: String?) {
    if (message != null) {
        Spacer(Modifier.height(12.dp))
        Text(message, color = ZipError, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
internal fun AuthButton(label: String, loading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !loading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ZipGreen)
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
        } else {
            Text(label, style = MaterialTheme.typography.titleMedium)
        }
    }
}