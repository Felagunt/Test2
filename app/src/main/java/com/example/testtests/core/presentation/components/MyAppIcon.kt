package com.example.testtests.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.testtests.R

@Composable
fun MyAppIcon(
    cont: String? = null
): ImageVector {
    return ImageVector.vectorResource(R.drawable.baseline_bookmark_border_24)
}