package com.example.barcodebites.Graphs

import Screens.BearbeitenProfileScreen
import Screens.PostScanScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.barcodebites.Screen


fun NavGraphBuilder.profilBearbeitenNavGraph(navController: NavHostController) {
    navigation(
        route = PROFILBEAREITEN,
        startDestination = Screen.BearbeitenProfileScreen.route
    ) {
        composable(route = Screen.BearbeitenProfileScreen.route) {
            BearbeitenProfileScreen(
                onOkClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.MainProfileScreen.route)
                }
            )
        }
        //evtl noch mehr Screens etc
    }
}