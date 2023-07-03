package com.example.barcodebites.core

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.R
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.inheriting.ViewModelFactory
import com.example.barcodebites.core.presentation.NavAppHost
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Authentication.presentation.AuthenticationViewModel
import com.example.barcodebites.feature_Authentication.presentation.LoginEvents
import com.example.barcodebites.ui.theme.mainPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    val viewModel = viewModel<AuthenticationViewModel>(
        factory = ViewModelFactory(AuthenticationRepositoryImplementation(context))
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = backStackEntry?.destination
    viewModel.onEvent(LoginEvents.Init())
    Scaffold(
        topBar = { TopBar(context) },
        bottomBar = {
            AnimatedVisibility(visible = currentDest?.route != Screen.LoginScreen.route) {
                BottomBar(
                    navController,
                    onLogout = {
                        viewModel.logout()
                    },
                    currentDest = currentDest
                )
            }
        },
    ) {
        if(viewModel.state.value.IS_LOGGED != null){
            val startDest = when (viewModel.state.value.IS_LOGGED) {
                true -> Screen.ScanScreen.route
                else -> Screen.LoginScreen.route
            }
            NavAppHost(navController, context, viewModel, it, startDest)
        }
        else{
            Box(modifier = Modifier.padding(it)){
                Text(text = "BLAH")
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController,
              currentDest:NavDestination?,
              onLogout: () -> Unit) {
    val screens = listOf(
        NavBarScreen.Scan,
        NavBarScreen.History,
        NavBarScreen.Profile,
        NavBarScreen.Logout
    )
    BottomNavigation {
        screens.forEach {
            AddItem(
                screen = it,
                currentDest = currentDest,
                navController = navController,
                onLogout = onLogout
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavBarScreen,
    currentDest: NavDestination?,
    navController: NavHostController,
    onLogout: () -> Unit
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = { Icon(screen.icon, "${screen.title} Icon") },
        selected = currentDest?.hierarchy?.any { it.route == screen.route } == true,
        alwaysShowLabel = false,
        enabled = true,
        onClick = {
            if (screen.route == NavBarScreen.Logout.route) {
                onLogout()
            } else {
                navController.navigate(screen.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(context: Context) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = mainPurple),
        title = { Text(context.getString(R.string.app_name)) }
    )
}
