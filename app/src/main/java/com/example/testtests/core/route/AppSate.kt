package com.example.testtests.core.route

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.common.data.AuthHandler
import javax.inject.Inject

class AppState @Inject constructor(
    val authHandler: AuthHandler
) {
    // For example, a flag to check if the user is logged in
    var isUserLoggedIn by mutableStateOf("")

    // You can extend this to hold more global states as needed
    var currentRoute by mutableStateOf("")

    // Function to update the login state
//    fun updateLoginState(isLoggedIn: Boolean) {
//        isUserLoggedIn = isLoggedIn
//    }
//    val LocalAuthHandler = compositionLocalOf<AuthHandler> {
//        error("No auth handler")
//    }

    fun updateCurrentRoute(route: String) {
        currentRoute = route
    }
}