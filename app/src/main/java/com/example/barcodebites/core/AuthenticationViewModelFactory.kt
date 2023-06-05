package com.example.barcodebites.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Authentication.presentation.LoginViewModel

@Suppress("UNCHECKED_CAST")
class AuthenticationViewModelFactory(private val repository: AuthenticationRepositoryImplementation) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(repository) as T
}
