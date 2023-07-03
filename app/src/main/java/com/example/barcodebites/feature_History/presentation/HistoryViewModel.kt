package com.example.barcodebites.feature_History.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.feature_History.data.HistoryRepositoryImplementation
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.util.Locale

class HistoryViewModel(private val repository: HistoryRepositoryImplementation) :
    ViewModel() {

    //stores the original list of products that the user has scanned and saved.
    // It also has a mutable state variable _state that stores the current state of the history feature,
    // including the list of products to display and the current filters applied to the list.
    private var ORIGINAL_LIST = listOf<Pair<Product, Boolean>>()
    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state


    //filters the original list of products based on the current filters applied to the list. The filtered list is stored in the _state variable.
    private fun filterProducts(){
        _state.value = state.value.copy(LIST = ORIGINAL_LIST.filter { // ganze liste
            _state.value.FILTERS.all {filter ->
                val value = filter.data.value
                when(filter.id){
                    "isLiked" -> {
                        if(value as Boolean) it.first.isLiked
                        else true
                    }
                    "isEdible" -> {
                        if(value as Boolean) !it.second
                        else true
                    }
                    else -> {
                        if(value.toString().isNotEmpty()){
                            val search = value.toString().lowercase(Locale.ROOT)
                            it.first.productName.lowercase(Locale.ROOT).contains(search) || it.first.brand.lowercase(Locale.ROOT).contains(search)
                        }
                        else true
                    }
                }
            }
        })
    }
    //takes a Product object and returns its ID
    fun getProductId(product: Product): String {
        return repository.getId(product)
    }

    //takes a HistoryEvents object and performs the appropriate action based on the event
    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.Init -> {
                repository.db.collection(repository.PRODUCTS_COLLECTION)
                    .whereEqualTo("userEmail", repository.getUser())
                    .addSnapshotListener { value, e ->
                        if (e != null) {
                            Log.w("Error", "Listen failed.", e)
                            return@addSnapshotListener
                        }

                        viewModelScope.launch {
                            _state.value = state.value.copy(LIST = value!!.map { doc ->
                                val product = doc.toObject<Product>()
                                Pair(product, repository.checkNonEdibility(product))
                            })
                            ORIGINAL_LIST = state.value.LIST
                        }
                    }
                filterProducts() //is called when the user applies filters to the list of products.
            }

            is HistoryEvents.Remove -> {
                viewModelScope.launch {
                    repository.deleteProduct(event.productId)
                }
            }

            is HistoryEvents.Update -> {
                viewModelScope.launch {
                    repository.upsertProduct(event.product)
                }
            }

            is HistoryEvents.Filter -> {
                _state.value = state.value.copy(FILTERS = event.filters)
                filterProducts()
            }
        }
    }
}