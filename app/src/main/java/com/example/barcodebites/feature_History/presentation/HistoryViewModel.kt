package com.example.barcodebites.feature_History.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.feature_History.data.HistoryRepositoryImplementation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepositoryImplementation) :
    ViewModel() {
    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state

    private fun getProducts(): Flow<List<Product>> = repository.getAllProducts()

    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.Init -> {
                viewModelScope.launch {
                    getProducts().collect {
                        _state.value = state.value.copy(LIST = it)
                    }
                }
            }
            is HistoryEvents.Remove -> {
                viewModelScope.launch {
                    repository.deleteProduct(event.productId)
                }
            }

            is HistoryEvents.Update -> {
                viewModelScope.launch {
                    repository.updateProduct(event.product)
                }
            }
        }
    }
}