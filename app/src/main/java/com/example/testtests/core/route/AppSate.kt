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
    // You can extend this to hold more global states as needed
    var currentRoute by mutableStateOf("")

    fun updateCurrentRoute(route: String) {
        currentRoute = route
    }
}