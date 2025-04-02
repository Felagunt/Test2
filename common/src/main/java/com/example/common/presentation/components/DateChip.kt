package com.example.common.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.utils.toDate

@Composable
fun DateChip(
    date: String,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Text(
            text = "${date.toDate().dayOfMonth} ${date.toDate().month} ${date.toDate().year}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
        )
    }

}