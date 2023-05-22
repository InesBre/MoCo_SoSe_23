package com.example.barcodebites.Graphs

import Screens.ForgotScreen
import Screens.RegisterScreen
import Screens.StartScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.barcodebites.NavBarItem
import com.example.barcodebites.Screen

fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {
    navigation(
        route = LOGIN,
        startDestination = Screen.StartScreen.route
    ) {
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(
                onSignInClick = {
                    navController.popBackStack()
                    navController.navigate(LOGIN)
                }
            )
        }
        composable(route = Screen.StartScreen.route) {
            StartScreen(
                    onGoClick = {
                        navController.popBackStack()
                        navController.navigate(MAIN)
                    },
                    onForgotClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ForgotScreen.route)
                    },
                    onRegisterClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.RegisterScreen.route)
                    }
            )

        }
        composable(route = Screen.ForgotScreen.route) {
            ForgotScreen(
                onLetsGoClick = {
                    navController.popBackStack()
                    navController.navigate(LOGIN)
                }
            )
        }
    }
}




/*
sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}*/