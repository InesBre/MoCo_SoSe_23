package com.example.barcodebites.feature_Scan.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.barcodebites.core.ScanViewModelFactory

@Composable
fun ScanScreen(
    navController: NavController,
    factory: ScanViewModelFactory,
    viewModel: ScanViewModel = viewModel(factory = factory)
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //TopBar()//only ornamental, in middle
        //RunScanner()
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = "Let me scan for you",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 60.dp)
                    .clickable { TODO("!") }
            )
        }
        //TODO logik at info from scanner -> to postScanScreen and display Info
    }
}