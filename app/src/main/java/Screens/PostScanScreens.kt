package Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PostScanScreen( onBackClick: () -> Unit){
    //TODO logik etc, on scan display info into neu screen
    //needed: foodfacts API, way to display info coming from foodfacts

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .height(30.dp)
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "toScanScreen",
                modifier = Modifier.clickable { onBackClick() })
            Text(text = "back")
        }
    }
}