package com.example.barcodebites.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation
import com.example.barcodebites.feature_Scan.presentation.ScanViewModel

@Suppress("UNCHECKED_CAST")
class ScanViewModelFactory(private val repository: ScanRepositoryImplementation) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ScanViewModel(repository) as T
}
