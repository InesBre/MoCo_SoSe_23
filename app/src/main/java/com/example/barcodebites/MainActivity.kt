package com.example.barcodebites


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.ui.theme.BarcodeBitesTheme

//import com.google.mlkit.vision.barcode.BarcodeScanner

//TODO Kamera permission richtig

class MainActivity : ComponentActivity() {

    //für navigation versuch
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeBitesTheme {
                StartScreen(navController = rememberNavController())
                //StartScreen()
                //Bars()

                //für navigation versuch
                /*navController = rememberNavController()
                AppNav(navController = navController)*/
            }
        }
    }
}





