package com.example.barcodebites.feature_Scan.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.barcodebites.RunScanner
import com.example.barcodebites.core.inheriting.ViewModelFactory

@Composable
fun ScanScreen(
    navController: NavController,
    factory: ViewModelFactory,
    viewModel: ScanViewModel = viewModel(factory = factory)
) {
    val product = viewModel.state.value.DIALOG_PRODUCT
    fun onClose(save: Boolean = false) {
        viewModel.onEvent(ScanEvents.Choice(save))
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (product === null) {
            RunScanner(
                context = navController.context
            ) { result ->
                viewModel.onEvent(ScanEvents.Scanned(code = result))
            }
        } else {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(text = "Speicherbestätigung")
                },
                text = {
                    val edible = when (true) {
                        true -> "den Präferenzen entsprechend"
                        else -> "nicht den Präferenzen entsprechend"
                    }
                    Column {
                        Text(text = "Produkt ${product.productName} ist $edible")
                        Text(text = "Wollen Sie es speichern?")
                    }
                },
                confirmButton = {
                    Button(onClick = { onClose(true) }) {
                        Text("Produkt speichern")
                    }
                },
                dismissButton = {
                    Button(onClick = { onClose(false) }) {
                        Text("Produkt nicht speichern")
                    }
                }
            )
        }
    }
}
