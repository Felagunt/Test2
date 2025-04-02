package com.example.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.presentation.components.ForgotRow

@Composable
fun LogInScreenRoot(
    viewModel: LogInViewModel = hiltViewModel(),
    onLogInClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LogInScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LogInAction.OnLogInClick -> {
                    onLogInClick()
                }

                else ->
                    viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun LogInScreen(
    state: LoginState,
    onAction: (LogInAction) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current
    var enabled = remember { mutableStateOf(state.isEnabled) }

    Column(
        modifier = Modifier
//            .padding(top = 20.dp)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier
//                .padding(horizontal = 12.dp)
        )

        TextField(
            value = userEmail.value,
            onValueChange = {
                userEmail.value = it
                onAction(LogInAction.OnEmailChange(it))
            },
            placeholder = {
                Text(
                    text = "example@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            },
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.surface,
            )
        )


        Text(
            text = "Пароль",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        TextField(
            value = userPassword.value,
            onValueChange = {
                userPassword.value = it
                onAction(LogInAction.OnPasswordChange(it))
            },
            placeholder = {
                Text(
                    text = "Введите пароль",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            },
            shape = RoundedCornerShape(32.dp),

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.surface,
            )
        )



        Button(
            onClick = {
                onAction(LogInAction.OnLogInClick)
            },
            enabled = state.isEnabled,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Вход",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        ForgotRow()

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface,
            thickness = 2.dp
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    uriHandler.openUri("https://vk.com/")
                },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(
                    text = "VK",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(
                onClick = {
                    uriHandler.openUri("https://ok.ru/")
                },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF98509))
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}