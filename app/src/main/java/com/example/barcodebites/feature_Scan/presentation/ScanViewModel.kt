package com.example.barcodebites.feature_Scan.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation

class ScanViewModel(private val repository: ScanRepositoryImplementation) :
    ViewModel() {
    private val _state = mutableStateOf(ScanState())
    val state: State<ScanState> = _state

}