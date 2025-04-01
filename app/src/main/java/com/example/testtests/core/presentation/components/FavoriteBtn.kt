package com.example.testtests.core.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testtests.R
import com.example.testtests.domain.models.Course

@Composable
fun FavoriteBtn(
    onClick: (Course) -> Unit,
    course: Course,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                onClick.invoke(course)
            }
        ) {
            Icon(
                painter = if(course.hasLike) {
                    painterResource(R.drawable.baseline_bookmark_24)
                } else {
                    painterResource(R.drawable.baseline_bookmark_border_24)
                },
                contentDescription = null,
                tint = if (course.hasLike ) {
                    MaterialTheme.colorScheme.secondary//TODO
                } else {
                    MaterialTheme.colorScheme.background
                }
            )
        }

    }

}