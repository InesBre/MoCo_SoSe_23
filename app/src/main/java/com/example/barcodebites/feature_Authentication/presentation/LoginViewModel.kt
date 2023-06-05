package com.example.barcodebites.feature_Authentication.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.example.barcodebites.feature_Authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthenticationRepositoryImplementation,private val navController: NavController)
    : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(IS_BUSY = true)
                    val pw = repository.getPW(event.email)
                    if(event.password == pw){
                        navController.navigate(Screen.ScanScreen.route)
                    }
                    else _state.value = state.value.copy(IS_BUSY = false)
                }
            }
            is LoginEvents.Register -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(IS_BUSY = true)
                    repository.addUser(User(
                        email = event.email,
                        password = event.password
                    ))
                    _state.value = state.value.copy(IS_BUSY = false)
                }
            }
        }
    }
}