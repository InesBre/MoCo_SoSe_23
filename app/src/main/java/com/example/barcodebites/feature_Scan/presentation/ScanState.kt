package com.example.barcodebites.feature_Scan.presentation

import com.example.barcodebites.core.data.entities.Product

data class ScanState(
    val EMPTY: Boolean = true,
    val IS_BUSY: Boolean = false,
    val DIALOG_PRODUCT: Product? = null
)