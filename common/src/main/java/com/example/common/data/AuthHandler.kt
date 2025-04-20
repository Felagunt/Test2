package com.example.common.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthHandler @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    suspend fun logIn() {
        dataStoreManager.setLoggedIn(true)
        _isLoggedIn.value = true
    }

    suspend fun logOut() {
        dataStoreManager.setLoggedIn(false)
        _isLoggedIn.value = false
    }

    suspend fun clear() {
        dataStoreManager.clearData()//TODO
    }
}