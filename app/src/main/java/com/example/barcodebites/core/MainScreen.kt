package com.example.barcodebites.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.R
import com.example.barcodebites.ui.theme.mainPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar ={ NavBar(navController = navController) },

        ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it))
        { //NavBarNav(navController = navController)
            //TODO durch MainNav(navController = navController) ersetzen wenn fertig gebaut
            //MainNavGraph(navController = navController)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = mainPurple),
        title = { Text(R.string.app_name.toString())}
        )
}