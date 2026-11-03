package com.example.zipcart.frontend.screens.auth


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcart.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        val e = email.trim()
        validate(e, password)?.let { fail(it); return }
        submit { repository.login(e, password) }
    }

    fun signUp(name: String, email: String, password: String) {
        val e = email.trim()
        if (name.isBlank()) { fail("Please enter your name"); return }
        validate(e, password)?.let { fail(it); return }
        submit { repository.signUp(name.trim(), e, password) }
    }

    fun reset() {
        _state.value = AuthUiState()
    }

    private fun validate(email: String, password: String): String? = when {
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }

    private fun fail(message: String) {
        _state.value = AuthUiState(error = message)
    }

    private fun submit(block: () -> Result<Unit>) {
        viewModelScope.launch {
            _state.value = AuthUiState(isLoading = true)
            delay(500) // small delay so the loading state is visible
            _state.value = block().fold(
                onSuccess = { AuthUiState(success = true) },
                onFailure = { AuthUiState(error = it.message ?: "Something went wrong") }
            )
        }
    }
}