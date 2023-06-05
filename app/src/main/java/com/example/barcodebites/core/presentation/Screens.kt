package com.example.barcodebites.core.presentation


//TODO name mit features abgleichen

sealed class Screen(val route: String){
    //entry, login screen nicht gebraucht, schon in startscreen enthalten
    object LoginScreen: Screen("login")
    object ScanScreen: Screen("scan")

    //evtl mehr screens für bearbeitung profile oä?!
    //profil
    object MainProfileScreen: Screen("mainprofile")
    object BearbeitenProfileScreen: Screen("bearbeitenprofile")

}