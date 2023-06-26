package com.example.barcodebites.feature_History.presentation

import com.example.barcodebites.core.data.entities.Product

sealed class HistoryEvents {
    data class Init(
        val string: String = "STRING"
    ): HistoryEvents()

    data class Update(
        val product: Product
    ): HistoryEvents()

    data class Remove(
        val productId: Int
    ): HistoryEvents()
}