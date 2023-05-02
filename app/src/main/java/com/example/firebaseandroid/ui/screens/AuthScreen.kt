package com.example.firebaseandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseandroid.Routes
import com.example.firebaseandroid.viewmodels.auth.DummyAuthViewModel
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.firebaseandroid.viewmodels.auth.AuthViewModelInterface
import com.example.firebaseandroid.viewmodels.log.LogViewModelInterface

@Composable
fun AuthScreen(authViewModel: AuthViewModelInterface,
               navController: NavController,
               logViewModel: LogViewModelInterface) {

    val screenName = "AuthScreen"
    var showDialog = remember { mutableStateOf(false) }
    var dialogMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(onSuccess = { isLogged ->
            if (isLogged) {
                navController.navigate(Routes.WALL_SCREEN)
            }
        }, onFailure = { exception ->
            logViewModel.crash(screenName, exception)
        })
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            text = { Text(dialogMessage.value) },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = authViewModel.email,
            onValueChange = { authViewModel.email = it },
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = authViewModel.password,
            onValueChange = { authViewModel.password = it },
            placeholder = { Text(text = "Password") },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.register(
                    onSuccess = {
                        logViewModel.log(screenName, "USER_REGISTERED")
                        navController.navigate(Routes.WALL_SCREEN)
                    },
                    onFailure = { exception ->
                        dialogMessage.value = exception.message.toString()
                        showDialog.value = true
                        logViewModel.crash(screenName, exception)
                    }
                )
            },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Button(
            onClick = {
                authViewModel.login(
                    onSuccess = {
                        logViewModel.log(screenName, "USER_LOGGED_IN")
                        navController.navigate(Routes.WALL_SCREEN)
                    },
                    onFailure = { exception ->
                        dialogMessage.value = exception.message.toString()
                        showDialog.value = true
                        logViewModel.crash(screenName, exception)
                    }
                )
            },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Login")
        }

        Button(
            onClick = {
                authViewModel.recoverPassword(
                    onSuccess = {
                        logViewModel.log(screenName, "PASSWORD_RECOVERED")
                        dialogMessage.value = "Password recuperado, comprueba tu correo"
                        showDialog.value = true
                    },
                    onFailure = { exception ->
                        dialogMessage.value = exception.message.toString()
                        showDialog.value = true
                        logViewModel.crash(screenName, exception)
                    }
                )
            },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Recuperar contraseña")
        }

        Button(
            onClick = {
                throw RuntimeException("Test Crash")
            },
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Test Crashlytics")
        }
    }

}

