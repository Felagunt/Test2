package com.example.ui.auth_check

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.auth.LogInViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

@Composable
fun AuthCheckScreen(
    onLoggedIn: () -> Unit,
    onNotLoggedIn: () -> Unit
) {

    val viewModel = hiltViewModel<LogInViewModel>()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(isLoggedIn) {
        if(isLoggedIn.last()) {//TODO
            onLoggedIn()
        } else {
            onNotLoggedIn()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}