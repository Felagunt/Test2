package com.example.testtests.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DateChip(
    date: String,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
        )
    }

}