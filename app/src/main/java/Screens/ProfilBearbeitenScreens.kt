package Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.barcodebites.Screen

@Composable//scollabale //evtl mehr screens für bearbeitung profile oä?!
fun BearbeitenProfileScreen( onOkClick: () -> Unit){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(){
        Text(text="BearteitenProfile")
        Text(text="back",
            modifier = Modifier.clickable { onOkClick() })
                }
            }
}
