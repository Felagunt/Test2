package com.example.testtests.presentation.auth

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false
)