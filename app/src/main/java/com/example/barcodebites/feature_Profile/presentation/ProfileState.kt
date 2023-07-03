package com.example.barcodebites.feature_Profile.presentation


import androidx.compose.runtime.MutableState

data class ProfileState(
    val EMPTY: Boolean = true,
    val PREFERENCES: List<Pair<String,Triple<String, String?, MutableState<Boolean>>>> = mutableListOf()
)
