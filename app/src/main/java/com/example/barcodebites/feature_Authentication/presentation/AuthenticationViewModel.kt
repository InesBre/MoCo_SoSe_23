package com.example.barcodebites.feature_Authentication.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.feature_Authentication.data.AuthenticationRepositoryImplementation
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: AuthenticationRepositoryImplementation)
    : ViewModel() {

    //holds the current state of the login screen
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    //takes a type and a value and  returns true if the value is valid for that type
    fun validateFields(type: String, value: String): Boolean {
        return when(type){
            "password" -> !isFieldEmpty(value) && value.length >= 6
            "email" -> !isFieldEmpty(value) && android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
            else -> false
        }
    }

    //checks if a given string is empty or null
    fun isFieldEmpty(value:String?):Boolean{
        return value.isNullOrBlank() || value.isEmpty()
    }
    //checks if the user is currently logged in by calling the isLoggedIn method of the  AuthenticationRepositoryImplementation.
    private fun checkLogin(): Boolean {
        return repository.isLoggedIn()
    }
    //logs the user out by calling the logout method of the AuthenticationRepositoryImplementation.
    fun logout() {
        _state.value = state.value.copy(IS_SUCCESS = false)
        repository.logout()
    }

//handles events that are triggered by the UI layer. It takes an instance of LoginEvents and an
//optional NavController as parameters. The Init event sets the initial state of the login screen. The Login event
//logs the user in by calling the login method of the AuthenticationRepositoryImplementation. If the login is
//successful, it navigates to the ScanScreen and displays a success message. If the login fails due to too many login
//attempts, it displays an error message. If the login fails for any other reason, it displays a generic error message.

    fun onEvent(event: LoginEvents, navController: NavController? = null) {
        when (event) {
            is LoginEvents.Init -> {
                _state.value = state.value.copy(IS_LOGGED = checkLogin())
            }
            is LoginEvents.Login -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(IS_BUSY = true)
                    when (repository.login(event.email, event.password)) {
                        200 -> navController?.navigate(Screen.ScanScreen.route)
                        429 -> Toast.makeText(navController?.context,"Zu viele Login versuche", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(navController?.context,"Login fehlgeschlagen", Toast.LENGTH_SHORT).show()
                    }
                    _state.value = state.value.copy(IS_BUSY = false)
                }
            }
//The Register event registers a new user by calling the addUser method of the AuthenticationRepositoryImplementation.
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
//The Reset event sends a password reset email to the user by calling the reset method of the AuthenticationRepositoryImplementation.
            //passwort zurücksetzten
            is LoginEvents.Reset -> {
                repository.reset(event.email)
                Toast.makeText(navController?.context,"Reset Email an ${event.email} gesendet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}