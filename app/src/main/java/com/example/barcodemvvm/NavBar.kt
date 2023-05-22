package com.example.barcodemvvm

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.barcodemvvm.ui.theme.mainPurple


sealed class NavBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Profile: NavBarItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object Home: NavBarItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object History: NavBarItem(
        route = "history",
        title = "History",
        icon = Icons.Default.Search
    )
}

//different NavBar to try

@Composable
fun NavBar(navController: NavController){
    //list of destinations
    val screens = listOf(
        NavBarItem.Profile,
        NavBarItem.Home,
        NavBarItem.History
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(containerColor = mainPurple) {
        screens.forEach(){screen ->
            AddItem(screen = screen,
                currentDestination = currentDestination,
                navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavBarItem,
    currentDestination: NavDestination?,
    navController: NavController
){
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route } == true,
        onClick = { navController.navigate(screen.route) },
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "NavigationIcon")},
        alwaysShowLabel = false,
    )

}