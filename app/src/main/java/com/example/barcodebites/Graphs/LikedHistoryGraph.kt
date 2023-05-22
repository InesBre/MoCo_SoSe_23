package com.example.barcodebites.Graphs

import Screens.LikedHistoryScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.barcodebites.Screen


fun NavGraphBuilder.likedHistoryNavGraph(navController: NavHostController) {
    navigation(
        route = LIKEDHISTORY,
        startDestination = Screen.LikedHistoryScreen.route
    ) {
        composable(route = Screen.LikedHistoryScreen.route) {
            LikedHistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.MainHistoryScreen.route)
                }
            )
        }
        //evtl noch mehr Screens etc
    }
}