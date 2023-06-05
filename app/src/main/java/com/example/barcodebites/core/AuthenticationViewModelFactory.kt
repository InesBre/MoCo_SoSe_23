package com.example.barcodebites.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Authentication.presentation.LoginViewModel

@Suppress("UNCHECKED_CAST")
class AuthenticationViewModelFactory(private val repository: AuthenticationRepositoryImplementation, private val navController: NavController) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(repository, navController) as T
}
