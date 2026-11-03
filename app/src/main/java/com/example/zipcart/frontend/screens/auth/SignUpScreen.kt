package com.example.zipcart.frontend.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zipcart.frontend.components.ZipTextField
import com.example.zipcart.ui.theme.ZipGreenDark
import com.example.zipcart.ui.theme.ZipTextSecondary


@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) {
            viewModel.reset()
            onSignUpSuccess()
        }
    }

    AuthLayout(title = "Create account", subtitle = "Sign up and get groceries in 10 minutes") {
        ZipTextField(
            value = name,
            onValueChange = { name = it },
            label = "Full name",
            leadingIcon = Icons.Filled.Person
        )
        Spacer(Modifier.height(12.dp))
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
            label = "Password (min 6 characters)",
            leadingIcon = Icons.Filled.Lock,
            isPassword = true,
            imeAction = ImeAction.Done
        )
        AuthError(state.error)
        Spacer(Modifier.height(20.dp))
        AuthButton("Sign Up", state.isLoading) { viewModel.signUp(name, email, password) }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account?", color = ZipTextSecondary)
            TextButton(onClick = onLoginClick) {
                Text("Login", color = ZipGreenDark)
            }
        }
    }
}