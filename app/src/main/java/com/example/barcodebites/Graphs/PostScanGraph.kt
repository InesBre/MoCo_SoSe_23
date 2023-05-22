package com.example.barcodebites.Graphs

import Screens.LikedHistoryScreen
import Screens.PostScanScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.barcodebites.Screen


fun NavGraphBuilder.postScanNavGraph(navController: NavHostController) {
    navigation(
        route = POSTSCAN,
        startDestination = Screen.PostScanScreen.route
    ) {
        composable(route = Screen.PostScanScreen.route) {
            PostScanScreen(
                onBackClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.MainHistoryScreen.route)
                }
            )
        }
        //evtl noch mehr Screens etc
    }
}