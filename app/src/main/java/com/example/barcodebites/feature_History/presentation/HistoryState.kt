package com.example.barcodebites.feature_History.presentation

import com.example.barcodebites.core.data.entities.Product

data class HistoryState(
    val LIST: List<Pair<Product,Boolean>> = mutableListOf(), //alle listen
    val FILTERS: List<Filter> = mutableListOf() //alle filter
)