package com.example.courses.ui.courses_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.courses.domain.models.Course

@Composable
fun CourseListItem(
    course: Course,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Course) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = modifier,
        ) {
            HeaderListItem(
                course = course,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
            )
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = course.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = course.price,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Подробнее ->",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}