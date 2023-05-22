package Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.barcodebites.LazyLikedHistory
import com.example.barcodebites.LikedHistoryItemRepository
import com.example.barcodebites.Screen

@Composable
fun LikedHistoryScreen( onBackClick: () -> Unit ) {//scollabale
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .height(30.dp)
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "toHistoryItems",
                modifier = Modifier.clickable { onBackClick() })
            Text(text = "back")
        }
        val likedHistoryItemRepository = LikedHistoryItemRepository()
        val getAllLikedData = likedHistoryItemRepository.getAllData()
        //TODO does this work?!
        //TODO use indexnummer etc to track which item disliked -> remove from likedHistory List etc
        LazyColumn(
            userScrollEnabled = true,
            contentPadding = PaddingValues(all = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = getAllLikedData,
                key = { index, likedHistoryItem -> likedHistoryItem.name }) { index, likedHistoryItem ->
                Log.d("likedHistory", index.toString())
                LazyLikedHistory(likedHistoryItem = likedHistoryItem)
            }
        }
    }
}
