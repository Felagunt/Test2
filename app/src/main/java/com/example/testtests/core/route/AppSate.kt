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
    var currentRoute by mutableStateOf("") // Текущий маршрут
    var showBottomBar by mutableStateOf(true) // Нужно ли показывать Bottom Bar

    fun updateCurrentRoute(route: String) {
        currentRoute = route
        updateUIBasedOnRoute()
    }

    // Логика для управления видимостью компонентов UI, как Drawer или BottomBar
    private fun updateUIBasedOnRoute() {
        showBottomBar = when (currentRoute) {
            Dest.Home::class.qualifiedName,
            Dest.Favorite::class.qualifiedName,
            Dest.Profile::class.qualifiedName -> true
            else -> false
        }
    }
}
