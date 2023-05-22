package Screens

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.barcodebites.Graphs.MainNavGraph
import com.example.barcodebites.HistoryItemRepository
import com.example.barcodebites.LazyHistory
import com.example.barcodebites.NavBar
import com.example.barcodebites.RunScanner
import com.example.barcodebites.Screen
//import com.example.barcodebites.TopBar
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.mainPurple

@Composable
fun MainProfileScreen(onProfilBearbeitenClick: ()-> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text="MainProfile",
            modifier = Modifier.clickable { onProfilBearbeitenClick()}
        )
    }
}

@Composable //scollabale
fun MainHistoryScreen( onToLikedClick: () -> Unit) {

    //TODO logik, dass alle gescannten sachen hier auftauchen mit info etc
    //lazycolumn scrollable filled with historyitem s
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .height(30.dp)
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Liked Items", color = backBlack)
            Icon(imageVector = Icons.Default.ArrowForward,
                tint = backBlack,
                contentDescription = "toLikedItems",
                modifier = Modifier.clickable { onToLikedClick() })
        }
        val historyItemRepository = HistoryItemRepository()
        val getAllData = historyItemRepository.getAllData()
        //TODO does this work?!
        //TODO use indexnummer etc to track which item liked -> add to likedHistory List etc
        LazyColumn(
            userScrollEnabled = true,
            contentPadding = PaddingValues(all = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = getAllData,
                key = { index, historyItem -> historyItem.name }) { index, historyItem ->
                Log.d("mainHistory", index.toString())
                LazyHistory(historyItem = historyItem)
            }
        }
    }
}


@Composable//auch scanscreen
fun HomeScreen( onPostScanClick: () -> Unit) {


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //TopBar()//only ornamental, in middle
        RunScanner()
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = "Let me scan for you",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 60.dp)
                                    .clickable{ onPostScanClick() }
            )
        }
        //TODO logik at info from scanner -> to postScanScreen and display Info
    }
}

//evtl überflüssig?!?!
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreens(navController: NavHostController = rememberNavController()){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar ={ NavBar(navController = navController) },

        ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it))
        { //NavBarNav(navController = navController)
            //TODO durch MainNav(navController = navController) ersetzen wenn fertig gebaut
            MainNavGraph(navController = navController)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//evtl titel von aktueller seite oä immer anzeigen
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = mainPurple),
        title = { Text(" ") }
    )
}

