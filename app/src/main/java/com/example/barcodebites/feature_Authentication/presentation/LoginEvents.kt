package com.example.barcodebites.feature_Authentication.presentation

sealed class LoginEvents {
    data class Login(
        val email: String,
        val password: String
    ): LoginEvents()

    data class Register(
        val email: String,
        val password: String
    ): LoginEvents()
}