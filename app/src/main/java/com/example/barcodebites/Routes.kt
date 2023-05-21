package com.example.barcodebites

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


//NavRoutes für NavBar(Bottom)
@Composable
fun NavBarNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = NavBarItem.Home.route){
        composable(NavBarItem.Home.route){
            //tatsächliche definierte screens
           HomeScreen(navController = rememberNavController())
        }
        composable(NavBarItem.History.route){
            MainHistoryScreen(navController = rememberNavController())
        }
        composable(NavBarItem.Profile.route){
            MainProfileScreen(navController = rememberNavController())
        }
    }

}

// Composable to store NavHost
@Composable
fun AppNav(navController: NavHostController) {
//TODO startdestination zu StartScreen ändern (MainProfileScreen nur für versuch)
    NavHost(navController = navController, startDestination = Screen.MainProfileScreen.route) {
        // define all possible destinations
        //more possible screens to create?!
        composable(Screen.StartScreen.route) { StartScreen(navController = navController) }
        composable(Screen.RegisterScreen.route) { RegisterScreen(navController = navController) }

        composable(Screen.HomeScreen.route) { HomeScreen(navController = navController) }
        composable(Screen.PostScanScreen.route) { RegisterScreen(navController = navController) }

        composable(Screen.MainHistoryScreen.route) { MainHistoryScreen(navController = navController) }
        composable(Screen.LikeHistoryScreen.route) { LikeHistoryScreen(navController = navController) }

        composable(Screen.MainProfileScreen.route) { MainProfileScreen(navController = navController)}
        composable(Screen.BearbeitenProfileScreen.route) { BearbeitenProfileScreen(navController = navController) }
    }
}




