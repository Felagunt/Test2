package com.example.testtests.core.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.courses.domain.models.Course
import kotlinx.serialization.Serializable


sealed interface SubGraph {

    @Serializable
    data object DestGraph : SubGraph

    @Serializable
    data class CourseDetails(val id: Int) : SubGraph

    @Serializable
    data object Auth: SubGraph


}

sealed interface Auth {

    @Serializable
    data object AuthCheck: Auth

    @Serializable
    data object Onboarding : Auth

    @Serializable
    data object LogIn : Auth
}

sealed interface Dest {
    @Serializable
    data object Home : Dest

    @Serializable
    data object Favorite : Dest

    @Serializable
    data object Profile : Dest
}

enum class BottomNavigation(val label: String, val icon: ImageVector, val route: Dest) {
    HOME("home", Icons.Outlined.Home, Dest.Home),
    FAVORITE("Favorite", Icons.Filled.FavoriteBorder, Dest.Favorite),
    PROFILE("Profile", Icons.Filled.Person, Dest.Profile)
}
