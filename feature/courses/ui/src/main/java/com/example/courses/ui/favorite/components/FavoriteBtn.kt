package com.example.courses.ui.favorite.components

import android.content.res.Resources
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.example.courses.domain.models.Course
import com.example.courses.ui.R

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
                }
                ,
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