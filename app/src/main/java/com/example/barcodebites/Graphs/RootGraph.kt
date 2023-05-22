package com.example.barcodebites.Graphs

import Screens.MainScreens
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
//import com.example.barcodebites.MainScreens
import com.example.barcodebites.Screen
//import com.example.barcodebites.authNav


    const val ROOT = "rootgraph"
    const val LOGIN = "logingraph"
    const val MAIN = "maingraph"
    const val POSTSCAN = "postscangraph"
    const val LIKEDHISTORY = "likedhistorygraph"
    const val PROFILBEAREITEN = "profilbearbeitengraph"

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = ROOT,
        startDestination = LOGIN
    ) {
        loginNavGraph(navController = navController)
        composable(route = MAIN) {
            MainScreens()
        }
    }
}