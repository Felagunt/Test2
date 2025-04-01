package com.example.testtests.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtests.core.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            signIn()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var observeSignIn: Job? = null

    fun onAction(action: LogInAction) {
        when (action) {
            is LogInAction.OnEmailChange -> {
                updateUserEmail(action.email)
                signIn()
            }

            is LogInAction.OnLogInClick -> {
                //signIn()
            }

            is LogInAction.OnPasswordChange -> {
                updateUserPassword(action.password)
                signIn()
            }
        }
    }

    private fun updateUserEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun updateUserPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun signIn() {
        if (_state.value.email?.isValidEmail() == true && _state.value.password?.isNotEmpty() == true) {
            _state.update {
                it.copy(
                    isEnabled = true
                )
            }
        }
    }

}