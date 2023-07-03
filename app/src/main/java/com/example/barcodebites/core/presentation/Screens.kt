package com.example.barcodebites.core.presentation



//TODO name mit features abgleichen

sealed class Screen(val route: String){
    object LoginScreen: Screen("login")
    object ScanScreen: Screen("scan")
    object HistoryScreen: Screen("history")
    object ProfileScreen: Screen("profile")
}