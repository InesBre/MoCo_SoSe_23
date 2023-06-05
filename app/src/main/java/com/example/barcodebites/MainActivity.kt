package com.example.barcodebites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.core.AuthenticationViewModelFactory
import com.example.barcodebites.core.data.BaBiDb
import com.example.barcodebites.core.presentation.util.NavAppHost
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.ui.theme.BarcodeBitesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BarcodeBitesTheme {
                val navController = rememberNavController()
                NavAppHost(navController,application)
            }
        }
    }
}