package com.example.barcodebites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.backWhite



class HistoryItem(val name: String){}

class HistoryItemRepository{
    fun getAllData(): List<HistoryItem>{
        return listOf(
            HistoryItem("Huhn"),
            HistoryItem("Brot"),
            HistoryItem("Beispiel"),
            HistoryItem("Nudeln"),
            HistoryItem("Suppe"),
            HistoryItem("Banane"),
            HistoryItem("Apfel"),
            HistoryItem("Käse"),
            HistoryItem("Salami"),
            HistoryItem("Zwiebel"),
            HistoryItem("Mandel"),
            HistoryItem("Kirsche"),
            HistoryItem("Melone"),
            HistoryItem("Muelsi"),
        )
    }
}



@Composable
//TODO logik list zu füllen mit eigentlichen daten
fun LazyHistory(historyItem: HistoryItem){
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(70.dp)){
        Box(modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .background(color = backBlack)){
            Text(text = "Bild", color = backWhite)
        }
        Text(text = "${historyItem.name}")
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "notliked",
            modifier = Modifier.clickable {
                /*TODO logik: anderes Icon(gefülltes herz) + bei weiterem cklicken zurück
                + logik, dass die reihe (plus infos ) in likedHistory auftaucht
                 */
            })
    }
}