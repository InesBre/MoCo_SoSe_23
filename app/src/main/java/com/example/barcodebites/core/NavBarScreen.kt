package com.example.barcodebites.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Scan : NavBarScreen(
        route = "scan",
        title = "Scanner",
        icon = Icons.Default.Add
    )

    object History : NavBarScreen(
        route = "history",
        title = "Produkte",
        icon = Icons.Default.Search
    )

    object Profile : NavBarScreen(
        route = "profile",
        title = "Profil",
        icon = Icons.Default.Person
    )

    object Logout : NavBarScreen(
        route = "logout",
        title = "Logout",
        icon = Icons.Default.Lock
    )
}