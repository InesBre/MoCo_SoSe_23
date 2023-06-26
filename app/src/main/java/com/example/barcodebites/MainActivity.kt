package com.example.barcodebites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.barcodebites.core.MainScreen
import com.example.barcodebites.ui.theme.BarcodeBitesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BarcodeBitesTheme {
                MainScreen(context = application)
            }
        }
    }
}