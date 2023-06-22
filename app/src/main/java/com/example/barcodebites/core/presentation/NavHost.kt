package com.example.barcodebites.core.presentation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.barcodebites.core.inheriting.ViewModelFactory
import com.example.barcodebites.feature_Authentication.presentation.AuthenticationViewModel
import com.example.barcodebites.feature_Authentication.presentation.LoginScreen
import com.example.barcodebites.feature_History.data.HistoryRepositoryImplementation
import com.example.barcodebites.feature_History.presentation.HistoryScreen
import com.example.barcodebites.feature_Profile.data.ProfileRepositoryImplementation
import com.example.barcodebites.feature_Profile.presentation.ProfileScreen
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation
import com.example.barcodebites.feature_Scan.presentation.ScanScreen

/*TODO pass parameters!!! etc
        composable(route= Screen.AddEditNoteScreen.route
        + "?noteId={noteId}&noteColor={noteColor}
         */
@Composable
fun NavAppHost(navController: NavController, context: Context, viewModel: AuthenticationViewModel, paddingValues: PaddingValues, startDest: String){

    NavHost(navController = navController as NavHostController, startDestination = startDest, Modifier.padding(paddingValues)){

        composable(Screen.LoginScreen.route){
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.ScanScreen.route){
            ScanScreen(
                navController = navController,
                factory = ViewModelFactory(ScanRepositoryImplementation(context))
            )
        }

        composable(Screen.HistoryScreen.route){
            HistoryScreen(
                factory = ViewModelFactory(HistoryRepositoryImplementation(context))
            )
        }

        composable(Screen.ProfileScreen.route){
            ProfileScreen(
                factory = ViewModelFactory(ProfileRepositoryImplementation(context))
            )
        }
    }
}