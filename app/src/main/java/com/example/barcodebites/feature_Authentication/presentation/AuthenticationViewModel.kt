package com.example.barcodebites.feature_Authentication.presentation

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: AuthenticationRepositoryImplementation)
    : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    suspend fun checkLogin(): Boolean {
        return repository.isLoggedIn()
    }

    fun logout() {
        _state.value = state.value.copy(IS_SUCCESS = false)
        repository.logout()
    }

    fun onEvent(event: LoginEvents, navController: NavController? = null) {
        when (event) {
            is LoginEvents.Init -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(IS_LOGGED = checkLogin())
                }
            }
            is LoginEvents.Login -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(IS_BUSY = true)
                    when (repository.login(event.email, event.password)) {
                        true -> navController?.navigate(Screen.ScanScreen.route)
                        else -> Toast.makeText(navController?.context,"Login fehlgeschlagen", Toast.LENGTH_SHORT).show()
                    }
                    _state.value = state.value.copy(IS_BUSY = false)
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