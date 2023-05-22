package com.example.barcodebites

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AUTH
/*
@Composable
fun RootNavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LOGIN
    ) {
        authNav(navController = navController)
        composable(route = Graph.HOME) {
            //HomeScreen()//evtl falsche bezeichnung
        }
    }
}


object Graph{
    const val ROOT = "rootgraph"
    const val LOGIN = "authgraph"
    const val HOME = "homegraph"
    const val DETAILS = "detailsgraph"
}

fun NavGraphBuilder.authNav(navController: NavHostController){
    navigation(
        route = Graph.AUTH,
        startDestination = Screen.StartScreen.route){
        composable(route = Screen.StartScreen.route){
            LoginContent(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                onSignUpClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }
        composable(route = Screen.RegisterScreen.route){
            ScreenContent(name = Screen.RegisterScreen.route){}
        }
    }
}

fun NavGraphBuilder.mainNav(navController: NavHostController){

}

fun NavGraphBuilder.profileNav(navController: NavHostController){
    navigation(
        route = Graph.PROFILE,
        startDestination = Screen.BearbeitenProfileScreen.route
    )
}







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
        composable(Screen.LikedHistoryScreen.route) {
            LikedHistoryScreen(navController = rememberNavController())
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
        //composable(Screen.LikeHistoryScreen.route) { LikeHistoryScreen(navController = navController) }

        composable(Screen.MainProfileScreen.route) { MainProfileScreen(navController = navController)}
        composable(Screen.BearbeitenProfileScreen.route) { BearbeitenProfileScreen(navController = navController) }
    }
}

*/


