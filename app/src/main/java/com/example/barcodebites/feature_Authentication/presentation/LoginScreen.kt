package com.example.barcodebites.feature_Authentication.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.barcodebites.core.AuthenticationViewModelFactory
import com.example.barcodebites.core.presentation.Screen
import com.example.barcodebites.ui.theme.backBlack
import com.example.barcodebites.ui.theme.backWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    factory: AuthenticationViewModelFactory,
    viewModel: LoginViewModel = viewModel(factory = factory)
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
                    },enabled = !viewModel.state.value.IS_BUSY) {
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
                    state = viewModel.state
                )
            }
        },
        content = {
            Form(
                email = email,
                password = password,
                registerText = "Registrieren",
                onLogin = {
                    viewModel.onEvent(
                        LoginEvents.Login(
                            email = email.value.text,
                            password = password.value.text
                        )
                    )
                },
                onRegister = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                state = viewModel.state
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
    onLogin: (() -> Unit)?,
    onRegister: () -> Unit,
    state: State<LoginState>
) {
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
            enabled = !state.value.IS_BUSY,
            label = { Text(text = "Email", color = backWhite) }
        )
        TextField(
            value = password.value,
            singleLine = true,
            onValueChange = { password.value = it },
            enabled = !state.value.IS_BUSY,
            label = { Text(text = "Passwort", color = backWhite) }
        )
        onLogin?.let {
            Button(onClick = onLogin, enabled = !state.value.IS_BUSY) {
                Text(text = "Login", color = backWhite)
            }
        }
        Button(onClick = onRegister, enabled = !state.value.IS_BUSY) {
            Text(text = registerText, color = backWhite)
        }
    }
}