package Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.barcodebites.Screen
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.backWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen( onSignInClick: () -> Unit){

    //TODO logik von anmelden, email?, etc
    //TODO nav register -> StartScreen
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Name") })
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password") })
            Box(modifier = Modifier
                .height(10.dp)
                .width(30.dp)
                .clickable { onSignInClick() }
            ) {
                Text(text = "SignIn")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(onGoClick: () -> Unit,
                onRegisterClick: () -> Unit,
                onForgotClick: () -> Unit){
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
                .height(30.dp)
                .width(70.dp)
                .clickable { onGoClick() }
            ) {
                Text(text = "Let´s Go!")
            }
            Box(modifier = Modifier
                .height(30.dp)
                .width(70.dp)
                .clickable { onRegisterClick() }
            ) {
                Text(text = "Register!")
            }
        }
        Box(modifier = Modifier
            .height(30.dp)
            .width(70.dp)
            .clickable { onForgotClick() }
        ) {
            Text(text = "Forgot password?")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotScreen( onLetsGoClick: () -> Unit){
    //TODO logik von anmelden, email?, etc
    //TODO nav register -> StartScreen
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Name") })
            OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password") })
            Box(modifier = Modifier
                .height(10.dp)
                .width(30.dp)
                .clickable {  onLetsGoClick() }
            ) {
                Text(text = "Let´s Go!")
            }
        }
    }
}