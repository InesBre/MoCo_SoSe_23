package com.example.barcodebites.Graphs

import Screens.HomeScreen
import Screens.LikedHistoryScreen
import Screens.MainHistoryScreen
import Screens.MainProfileScreen
import Screens.RegisterScreen
import com.example.barcodebites.Screen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.barcodebites.NavBarItem

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = MAIN,
        startDestination = NavBarItem.Home.route
    ) {
        composable(route = NavBarItem.Home.route) {
            HomeScreen(
                //später nicht mehr onclick sondern automatisch nach scan
                //TODO logik dafür
                onPostScanClick = {
                    navController.navigate(POSTSCAN)

                }
            )
        }
        postScanNavGraph(navController = navController)
        composable(route = NavBarItem.History.route) {
            MainHistoryScreen(
                //evtl vers buttons mit vers bearteitungsmöglichkeiten
                //TODO -> später hinzufügen
                onToLikedClick = {
                    navController.navigate(LIKEDHISTORY)
                }
            )
        }
        profilBearbeitenNavGraph(navController = navController)
        composable(route = NavBarItem.Profile.route) {
            MainProfileScreen(
                onProfilBearbeitenClick = {
                    navController.navigate(PROFILBEAREITEN)
                }
            )
        }
        likedHistoryNavGraph(navController = navController)
    }
}






/*
sealed class PostScanScreen(val route: String) {
    object PostScan : PostScanScreen(route = "postscan")
}
sealed class LikedHistoryScreen(val route: String) {
    object LikedHistory : LikedHistoryScreen(route = "likedhistory")
}
sealed class ProfilBearbeitenScreen(val route: String) {
    object ProfilBearbeiten : ProfilBearbeitenScreen(route = "profilbearbeiten")
}*/