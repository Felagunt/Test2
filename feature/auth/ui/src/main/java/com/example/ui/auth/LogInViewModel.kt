package com.example.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.isValidEmail
import com.example.common.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

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


//    private val _isLoggedIn = MutableStateFlow(false)
//
//    init {
//        viewModelScope.launch {
//            dataStoreManager.isLoggedIn.collect { loggedIn ->
//                _isLoggedIn.value = loggedIn
//            }
//        }
//    }
//
//    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = dataStoreManager.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly, // Запускаем flow сразу
            initialValue = false
        )


    fun onAction(action: LogInAction) {
        when (action) {
            is LogInAction.OnEmailChange -> {
                updateUserEmail(action.email)
                signIn()
            }

            is LogInAction.OnLogInClick -> {
                //signIn()
                saveSession()
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

    private fun saveSession() {
        viewModelScope.launch {
            dataStoreManager.setLoggedIn(true)
        }
    }

}