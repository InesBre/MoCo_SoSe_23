package com.example.barcodebites.core.inheriting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Authentication.presentation.AuthenticationViewModel
import com.example.barcodebites.feature_History.data.HistoryRepositoryImplementation
import com.example.barcodebites.feature_History.presentation.HistoryViewModel
import com.example.barcodebites.feature_Profile.data.ProfileRepositoryImplementation
import com.example.barcodebites.feature_Profile.presentation.ProfileViewModel
import com.example.barcodebites.feature_Scan.data.ScanRepositoryImplementation
import com.example.barcodebites.feature_Scan.presentation.ScanViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (private val repository: BaseRepositoryImplementation) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) ->
                AuthenticationViewModel(repository as AuthenticationRepositoryImplementation) as T
            modelClass.isAssignableFrom(ScanViewModel::class.java) ->
                ScanViewModel(repository as ScanRepositoryImplementation) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(repository as HistoryRepositoryImplementation) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository as ProfileRepositoryImplementation) as T
            else -> throw IllegalArgumentException("View class not found!")
        }
    }
}