package com.example.barcodebites.feature_Authentication.presentation

data class LoginState (
    val EMPTY: Boolean = true, //leer
    val IS_BUSY: Boolean = false,// laden
    val IS_SUCCESS: Boolean? = null, // erolgreich
    val IS_LOGGED: Boolean? = null, // ohne einloggen
    //val PRODUCTS_LENGTH: Int = 0
)