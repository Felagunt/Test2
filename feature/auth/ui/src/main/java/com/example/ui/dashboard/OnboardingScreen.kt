package com.example.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth.ui.R
import com.example.common.presentation.components.RowChips
import com.example.ui.auth.LogInViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Onboarding(
    viewModel: LogInViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigationNext: () -> Unit,
    onNavigationToLogin: () -> Unit
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    val rowList = mutableListOf(
        "ОС Администрирование", "RabbitMQ", "Траффик", "Маркетинг", "B2B маркетинг",
        "Google аналитика", "Исследователь", "Веб-аналитика", "Big Data", "Ci/Cd",
        "Веб-дизайн", "Cinema 4D", "Промпт", "www", "Three.js", "Парсинг", "Python-разработчик"
    )
    Box(
        modifier = Modifier

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.tittle_onbording),
                minLines = 2,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(32.dp)
                    //.align(Alignment.CenterHorizontally)
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                maxLines = 5
            ) {
                rowList.onEach { item ->
                    RowChips(item)
                }
            }
        }
        Button(
            onClick = {
                if(isLoggedIn) {
                    onNavigationNext()
                } else {
                    onNavigationToLogin()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = stringResource(R.string.continue_btn),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}