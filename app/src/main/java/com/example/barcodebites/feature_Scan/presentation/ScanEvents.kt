package com.example.barcodebites.feature_Scan.presentation

sealed class ScanEvents {
    data class Scanned(
        val code: String,
    ): ScanEvents()

    data class Choice(
        val save: Boolean,
    ): ScanEvents()
}