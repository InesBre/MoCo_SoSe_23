package com.example.barcodebites.feature_Authentication.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.barcodebites.R
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.backWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel
) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = backBlack,
        gesturesEnabled = false,
        drawerContent = {
            Column {
                Row() {
                    Button(onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }, enabled = !viewModel.state.value.IS_BUSY) {
                        Text("Schliessen")
                    }
                }
                Form(
                    email = email,
                    password = password,
                    registerText = "Registrierung abschliessen",
                    onLogin = null,
                    onRegister = {
                        viewModel.onEvent(
                            LoginEvents.Register(
                                email = email.value.text,
                                password = password.value.text
                            )
                        )
                        scope.launch {
                            email.value = TextFieldValue("")
                            password.value = TextFieldValue("")
                            drawerState.close()
                        }
                    },
                    viewModel = viewModel
                )
            }
        },
        content = {
            Form(
                email = email,
                password = password,
                registerText = "Registrieren",
                onLogin = {isReset ->
                    scope.launch {
                        if(isReset){
                            viewModel.onEvent(
                                LoginEvents.Reset(
                                    email = email.value.text
                                ), navController
                            )
                        }
                        else{
                            viewModel.onEvent(
                                LoginEvents.Login(
                                    email = email.value.text,
                                    password = password.value.text,
                                ), navController
                            )
                        }
                    }
                },
                onRegister = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                viewModel = viewModel
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(
    email: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    registerText: String,
    onLogin: ((isReset:Boolean) -> Unit)?,
    onRegister: () -> Unit,
    viewModel: AuthenticationViewModel
) {
    val passwordVisibility = remember { mutableStateOf(false) }
    val icon = when (passwordVisibility.value) {
        true -> painterResource(id = R.drawable.baseline_visibility_24)
        false -> painterResource(id = R.drawable.baseline_visibility_off_24)
    }
    val disableBtn =
        viewModel.state.value.IS_BUSY || viewModel.isFieldEmpty(email.value.text) || viewModel.isFieldEmpty(
            password.value.text
        )
    val dialogTitle = remember {
        mutableStateOf("")
    }
    val dialogText = remember {
        mutableStateOf("")
    }
    val showDialog = remember {
        mutableStateOf(false)
    }
    val basicCheck: () -> Boolean = {
        val isEmailValid = viewModel.validateFields("email", email.value.text)
        val isPwValid = viewModel.validateFields("password", password.value.text)
        var text = ""
        if (!isPwValid && !isEmailValid)
            text = "Email und Passwort nicht valide. Bitte überprüfen Sie Ihre Eingaben."
        if (!isEmailValid)
            text = "Email nicht valide. Bitte überprüfen Sie Ihre Eingaben"
        if (!isPwValid)
            text = "Passwort nicht valide. Bitte überprüfen Sie Ihre Eingaben. Das Passwort muss mindestens 6 Zeichen enthalten"
        if(text.isNotEmpty()) {
            val title = if(onLogin === null) "Fehler bei der Registrierung" else "Fehler beim Login"
            dialogTitle.value = title
            dialogText.value = text
            showDialog.value = true
        }
        isEmailValid && isPwValid
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email.value,
            singleLine = true,
            onValueChange = { email.value = it },
            enabled = !viewModel.state.value.IS_BUSY,
            label = { Text(text = "Email", color = backWhite) },
            isError = !viewModel.validateFields("email", email.value.text),
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Visibility Icon"
                )
            }
        )
        TextField(
            value = password.value,
            singleLine = true,
            onValueChange = { password.value = it },
            enabled = !viewModel.state.value.IS_BUSY,
            label = { Text(text = "Passwort", color = backWhite) },
            isError = !viewModel.validateFields("password", password.value.text),
            leadingIcon = {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Visibility Icon"
                )
            },
            trailingIcon = {
                Button(enabled = !viewModel.isFieldEmpty(password.value.text), onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        onLogin?.let {
            Button(
                onClick = {
                    if (basicCheck()) onLogin(false)
                },
                enabled = !disableBtn
            ) {
                Text(text = "Login", color = backWhite)
            }
            Button(
                onClick = {
                    onLogin(true)
                },
                enabled = !viewModel.isFieldEmpty(email.value.text)
            ) {
                Text(text = "Passwort zurücksetzen", color = backWhite)
            }
        }
        Button(
            onClick = {
                if(onLogin !== null) onRegister()
                else if(basicCheck()) onRegister()
            },
            enabled = if(onLogin === null) true else !disableBtn
        ) {
            Text(text = registerText, color = backWhite)
        }

        if(showDialog.value){
            AlertDialog(
                onDismissRequest = { },
                title = { Text(text = dialogTitle.value) },
                text = { Text(text = dialogText.value) },
                confirmButton = {},
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Eingaben prüfen")
                    }
                }
            )
        }
    }
}