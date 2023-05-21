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



class LikedHistoryItem(val name: String){}

class LikedHistoryItemRepository{
    fun getAllData(): List<LikedHistoryItem>{
        return listOf(
            LikedHistoryItem("Huhn"),
            LikedHistoryItem("Brot"),
            LikedHistoryItem("Beispiel"),
            LikedHistoryItem("Nudeln"),
            LikedHistoryItem("Suppe"),
            LikedHistoryItem("Banane"),
            LikedHistoryItem("Apfel"),
            LikedHistoryItem("Käse"),
            LikedHistoryItem("Salami"),
            LikedHistoryItem("Zwiebel"),
            LikedHistoryItem("Mandel"),
            LikedHistoryItem("Kirsche"),
            LikedHistoryItem("Melone"),
            LikedHistoryItem("Muelsi"),
        )
    }
}



@Composable
//TODO logik list zu füllen mit eigentlichen daten
fun LazyLikedHistory(likedHistoryItem: LikedHistoryItem){
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(70.dp)){
        Box(modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .background(color = backBlack)){
            Text(text = "Bild", color = backWhite)
        }
        Text(text = "${likedHistoryItem.name}")
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "liked",
            modifier = Modifier.clickable {
                /*TODO logik: bei clicken zurück zu unliked und aus liked liste raus
               
                 */
            })
    }
}