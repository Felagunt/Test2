package com.example.ui.auth

sealed interface LogInAction {
    data class OnEmailChange(val email: String) : LogInAction
    data class OnPasswordChange(val password: String) : LogInAction
    data object OnLogInClick: LogInAction
}