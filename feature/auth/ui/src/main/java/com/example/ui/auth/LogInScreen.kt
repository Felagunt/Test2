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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth.ui.R
import com.example.common.presentation.components.ForgotRow
import com.example.common.presentation.components.LabeledText
import com.example.common.presentation.components.LabeledTextField
import com.example.common.utils.OK_URL
import com.example.common.utils.VK_URL

@Composable
fun LogInScreenRoot(
    viewModel: LogInViewModel = hiltViewModel(),
    onLogInClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLogInClick()
        }
    }

    LogInScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LogInAction.OnLogInClick -> {
                    //onLogInClick()
                    viewModel.onAction(action)
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
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {


        LabeledText(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LabeledTextField(
            label = stringResource(R.string.email_txt),
            value = userEmail.value,
            onValueChange = {
                userEmail.value = it
                onAction(LogInAction.OnEmailChange(it))
            },
            placeholder = stringResource(R.string.example_gmail_com),
            modifier = Modifier.padding(16.dp)
        )

        LabeledTextField(
            label = stringResource(R.string.password_txt),
            value = userPassword.value,
            onValueChange = {
                userPassword.value = it
                onAction(LogInAction.OnPasswordChange(it))
            },
            placeholder = stringResource(R.string.put_password),
            modifier = Modifier.padding(20.dp)
        )

        Button(
            onClick = {
                onAction(LogInAction.OnLogInClick)
//                    coroutineScope.launch {
//                        userPref.setLoggedIn(true)
//                    }
            },
            enabled = state.isEnabled,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 14.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        ForgotRow()

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface,
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    uriHandler.openUri(VK_URL)
                },
                modifier = Modifier
                    .padding(16.dp)
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
                    uriHandler.openUri(OK_URL)
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

//            Text(
//                text = stringResource(R.string.sign_in),
//                style = MaterialTheme.typography.displayMedium,
//                modifier = Modifier
//                    .padding(bottom = 20.dp)
//            )
//            Text(
//                text = "Email",
//                style = MaterialTheme.typography.bodyLarge,
////            modifier = Modifier
////                .padding(horizontal = 12.dp)
//            )
//
//            TextField(
//                value = userEmail.value,
//                onValueChange = {
//                    userEmail.value = it
//                    onAction(LogInAction.OnEmailChange(it))
//                },
//                placeholder = {
//                    Text(
//                        text = "example@gmail.com",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.surface
//                    )
//                },
//                shape = RoundedCornerShape(32.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
//                    unfocusedContainerColor = MaterialTheme.colorScheme.outline,
//                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
//                )
//            )
//
//
//            Text(
//                text = stringResource(R.string.password_txt),
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier
//                    .padding(top = 16.dp)
//            )
//
//            TextField(
//                value = userPassword.value,
//                onValueChange = {
//                    userPassword.value = it
//                    onAction(LogInAction.OnPasswordChange(it))
//                },
//                placeholder = {
//                    Text(
//                        text = stringResource(R.string.put_password),
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.surface
//                    )
//                },
//                shape = RoundedCornerShape(32.dp),
//
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
//                    unfocusedContainerColor = MaterialTheme.colorScheme.outline,
//                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
//                )
//            )