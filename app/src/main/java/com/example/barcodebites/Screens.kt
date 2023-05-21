package com.example.barcodebites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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


//Screens als Objecte einfügen, siehe auch github
sealed class Screen(val route: String){

    //entry, login screen nicht gebraucht, schon in startscreen enthalten
    object StartScreen: Screen("startscreen")
    object RegisterScreen: Screen("register")

    //scan screen auch main page?!
    object HomeScreen: Screen("home")
    object PostScanScreen: Screen("postscan")

    //historie
    object MainHistoryScreen: Screen("mainhistory")
    object LikeHistoryScreen: Screen("likehistory")

    //evtl mehr screens für bearbeitung profile oä?!
    //profil
    object MainProfileScreen: Screen("mainprofile")
    object BearbeitenProfileScreen: Screen("bearbeitenprofile")

}

//TODO braucht es wirklich login mit passwort etc?!
//wenn nicht, dann firebase anonymous authentication


//Rahmen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bars(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar ={ NavBar(navController = navController) },

    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it))
        { NavBarNav(navController = navController)
                        AppNav(navController = navController) //TODO prüfen ob so klappt
        }

    }
}

//TODO evtl optionaler zurück pfeil für tiefere Screens!!!

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//evtl titel von aktueller seite oä immer anzeigen
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = mainPurple),
        title = { Text(" ") }
    )
}


//wiederholende Elemente oä


//TODO potentielle noch andere finden


//Screens (mit wiederholenden Elementen zusammengebaut)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController){

    //TODO input check etc
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .background(color = backBlack)
                .height(50.dp)
                .width(500.dp),
                )
            { Text(text = "Logo", color = backWhite) }
            Text("Barcode - Bites")
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Name")})
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password")})
            Box(modifier = Modifier
                .height(10.dp)
                .width(30.dp)
                .clickable { navController.navigate(Screen.HomeScreen.route) }
            ) {
                Text(text = "Let´s Go!")
            }
            Box(modifier = Modifier
                .height(10.dp)
                .width(30.dp)
                .clickable { navController.navigate(Screen.RegisterScreen.route) }
            ) {
                Text(text = "Register!")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController){

    //TODO logik von anmelden, email?, etc
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Name")})
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password")})
            Box(modifier = Modifier
                .height(10.dp)
                .width(30.dp)
                .clickable { navController.navigate(Screen.HomeScreen.route) }
            ) {
                Text(text = "Let´s Go!")
            }
        }
    }
}


@Composable//auch scanscreen
fun HomeScreen(navController: NavController) {


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TopBar()//only ornamental, in middle
        RunScanner()
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = "Let me scan for you",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 60.dp)
            )
        }
        //NavBar(navController = )
    }
}

@Composable
fun PostScanScreen(navController: NavController){
    //TODO logik etc, on scan display info into neu screen
    //needed: foodfacts API, way to display info coming from foodfacts

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
}

@Composable //scollabale
fun MainHistoryScreen(navController: NavController){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        //TODO logik, dass alle gescannten sachen hier auftauchen mit info etc
        /*Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text(text = "MainHistory")
            //einträge mit lazy column
         }*/
        //Column titel?, nav to liked!
        //lazycolumn scrollable filled with historyitem s
        Column(modifier = Modifier.height(60.dp),
            horizontalAlignment = Alignment.End){
            Text(text = "Liked Items")
            Icon(imageVector = Icons.Default.ArrowForward,
                contentDescription = "toLikedItems",
                modifier = Modifier.clickable { navController.navigate(Screen.LikeHistoryScreen.route) })
        }
        val historyItemRepository = HistoryItemRepository()
        val getAllData = historyItemRepository.getAllData()
        //TODO does this work?!
        //TODO use indexnummer etc to track which item liked -> add to likedHistory List etc
        LazyColumn(userScrollEnabled = true,
            contentPadding = PaddingValues(all = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            itemsIndexed(items = getAllData,
                key = { index, historyItem -> historyItem.name }) { index, historyItem ->
                Log.d("mainHistory", index.toString())
                LazyHistory(historyItem = historyItem)
            }
        }
    }
}

@Composable
fun LikeHistoryScreen(navController: NavController) {//scollabale
    Column(
        modifier = Modifier.height(60.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "toHistoryItems",
            modifier = Modifier.clickable { navController.navigate(Screen.MainHistoryScreen.route)
                                            {popUpTo(Screen.MainHistoryScreen.route){inclusive = true}}})
        Text(text = "back")
    }
    val likedHistoryItemRepository = LikedHistoryItemRepository()
    val getAllLikedData = likedHistoryItemRepository.getAllData()
    //TODO does this work?!
    //TODO use indexnummer etc to track which item disliked -> remove from likedHistory List etc
    LazyColumn(userScrollEnabled = true,
        contentPadding = PaddingValues(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        itemsIndexed(items = getAllLikedData,
            key = { index, likedHistoryItem -> likedHistoryItem.name }) {
                index, likedHistoryItem ->
                Log.d("likedHistory", index.toString())
                LazyLikedHistory(likedHistoryItem = likedHistoryItem)
        }
    }
}


@Composable
fun MainProfileScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text="MainProfile",
            modifier = Modifier.clickable {
            navController.navigate(
                route = Screen.BearbeitenProfileScreen.route) }
    )}
}

//popBackStack() fun zum zurück gehen evtl
//TODO TopBar evtl optionalen peil sonst s.u
@Composable//scollabale //evtl mehr screens für bearbeitung profile oä?!
fun BearbeitenProfileScreen(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){Column(){
        Text(text="BearteitenProfile")
        Text(text="back",
            modifier = Modifier.clickable {
                navController.navigate(route=Screen.MainProfileScreen.route){
                    //screen poped of backstack
                    popUpTo(Screen.MainProfileScreen.route){inclusive = true}
                }
            })
    }}
}


/*
@Preview
@Composable
fun StartScreenPreview(){
    fun StartScreen(navController: NavController){}
}
*/

