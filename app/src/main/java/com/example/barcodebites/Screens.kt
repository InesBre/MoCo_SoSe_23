package com.example.barcodebites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.backWhite
import com.example.barcodebites.ui.theme.mainPurple



//AUTH Screens
//StartScreen, RegisterScreen, ForgotScreen



//Screens als Objecte einfügen, siehe auch github
sealed class Screen(val route: String){

    //entry, login screen nicht gebraucht, schon in startscreen enthalten
    //screen für vergessenes passwort oä?
    object StartScreen: Screen("startscreen")
    object RegisterScreen: Screen("register")
    object ForgotScreen: Screen("forgot")

    object MainScreens: Screen("mainscreen")

    //scan screen auch main page!
    object HomeScreen: Screen("home")
    object PostScanScreen: Screen("postscan")

    //historie
    object MainHistoryScreen: Screen("mainhistory")
    object LikedHistoryScreen: Screen("likedhistory")

    //evtl mehr screens für bearbeitung profile oä?!
    //profil
    object MainProfileScreen: Screen("mainprofile")
    object BearbeitenProfileScreen: Screen("bearbeitenprofile")

}

//TODO braucht es wirklich login mit passwort etc?!
//wenn nicht, dann firebase anonymous authentication

//Screens (mit wiederholenden Elementen zusammengebaut)

//popBackStack() fun zum zurück gehen evtl
//TODO TopBar evtl optionalen peil sonst s.u
//TODO evtl optionaler zurück pfeil für tiefere Screens!!!

