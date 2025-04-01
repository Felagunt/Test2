package com.example.courses.ui.courses_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.common.presentation.components.DateChip
import com.example.common.presentation.components.RatingChip
import com.example.courses.domain.models.Course

@Composable
fun HeaderListItem(
    course: Course,
    onFavoriteClick: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
    ) {
        AsyncImage(
            model ="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.IpJgQO7w8-XI4RcZ0iElWQHaE7%26pid%3DApi&f=1&ipt=d4497a5c287cd8d224fea126b015c09f7a332d6112853a9fd2e992c6659c0aa0&ipo=images",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(32.dp))
        )
        com.example.courses.ui.favorite.components.FavoriteBtn(
            onClick = onFavoriteClick,
            course = course,
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingChip(course.rate)
            DateChip(course.publishDate)
        }

    }

}
