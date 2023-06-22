package com.example.barcodebites.feature_Scan.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request.Method.GET
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation
import com.example.barcodebites.feature_Scan.domain.http.Response
import kotlinx.coroutines.launch

class ScanViewModel(private val repository: ScanRepositoryImplementation) :
    ViewModel() {
    private val _state = mutableStateOf(ScanState())
    val state: State<ScanState> = _state

    fun onEvent(event: ScanEvents) {
        when (event) {
            is ScanEvents.Scanned -> {
                _state.value = state.value.copy(IS_BUSY = true)
                val queries = listOf(
                    "fields=${repository.getFields().joinToString(",")}"
                )
                makeRequest("${event.code}?${queries.joinToString("&")}")
            }
            is ScanEvents.Choice -> {
                viewModelScope.launch {
                    if(event.save){
                        val product = state.value.DIALOG_PRODUCT!!
                        val found = repository.getProductByName(product.productName)
                        if(found == null) repository.insertProduct(product)
                        else repository.updateProduct(product)
                    }
                    _state.value = state.value.copy(DIALOG_PRODUCT = null)
                }
            }
        }
    }

    private fun makeRequest(
        barcode: String,
        method: Int = GET
    ) = viewModelScope.launch {
        when (val req = repository.call(barcode, method)) {
            is Response.Success -> {
                val product = repository.parseProduct(req.value)
                if(product === null) _state.value = state.value.copy(IS_BUSY = false)
                else _state.value = state.value.copy(DIALOG_PRODUCT = product)

            }
            is Response.Error -> Log.e("Network Error","${req.code}: ${req.message}")
            else -> Unit
        }
    }
}