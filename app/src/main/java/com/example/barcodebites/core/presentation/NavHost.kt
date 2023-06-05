package com.example.barcodebites.core.presentation.util

import android.content.Context
import com.example.barcodebites.feature_Authentication.presentation.LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.core.AuthenticationViewModelFactory
import com.example.barcodebites.core.CoreDao
import com.example.barcodebites.core.ScanViewModelFactory
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.feature_Authentication.data.AuthenticationDao
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation
import com.example.barcodebites.feature_Scan.presentation.ScanScreen

/*TODO pass parameters!!! etc
        composable(route= Screen.AddEditNoteScreen.route
        + "?noteId={noteId}&noteColor={noteColor}
         */
@Composable
fun NavAppHost(navController: NavHostController, context: Context){

    NavHost(navController = rememberNavController(), startDestination = Screen.LoginScreen.route){

        composable(Screen.LoginScreen.route){
            LoginScreen(
                navController = navController,
                factory = AuthenticationViewModelFactory(AuthenticationRepositoryImplementation(context),navController)
            )
        }

        composable(Screen.ScanScreen.route){
            ScanScreen(
                navController = navController,
                factory = ScanViewModelFactory(ScanRepositoryImplementation(context))
            )
        }

        /*composable(Screen.MainHistoryScreen.route){
            MainHistoryScreen (
                navController = rememberNavController(),
                onToLikedClick = {navController.navigate(Screen.LikedHistoryScreen.route)},
                //TODO onScanClicked implementieren, expliziete Seite für ScanInfos etc
            )
        }

        composable(Screen.LikedHistoryScreen.route){
            LikedHistoryScreen(
                        onBackClick = {navController.navigate(Screen.MainHistoryScreen.route)},
                //TODO onScanClicked...
                )
        }

        composable(Screen.HomeScreen.route){
            HomeScreen (
                onPostScanClick = {navController.navigate(Screen.PostScanScreen.route)},
                //TODO eigentlcih bei Scan automatisch!!!
            )
        }

        composable(Screen.PostScanScreen.route){
            PostScanScreen (
                onBackClick = {navController.navigate(Screen.HomeScreen.route)},
                //TODO weitere?
                    )
        }

        composable(Screen.MainProfileScreen.route){
            MainProfileScreen (
                onProfilBearbeitenClick = {navController.navigate(Screen.BearbeitenProfileScreen.route)},
            //TODO weitere?
                )
        }*/
    }
}