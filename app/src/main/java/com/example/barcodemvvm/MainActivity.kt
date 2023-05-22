package com.example.barcodemvvm


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.barcodemvvm.ui.theme.BarcodeMVVMTheme

//import com.google.mlkit.vision.barcode.BarcodeScanner

//TODO Kamera permission richtig





class MainActivity : ComponentActivity() {

    //für navigation versuch
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeMVVMTheme {
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
class MainActivity2 : ComponentActivity() {
   private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodeMVVMTheme {
                val navController = rememberNavController()
                AppNav(navController = navController, viewModel = viewModel)
            }
        }
    }
}

