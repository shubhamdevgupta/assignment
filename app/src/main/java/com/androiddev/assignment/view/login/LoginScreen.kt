package com.androiddev.assignment.view.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.androiddev.assignment.data.datastore.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    dataStoreManager: DataStoreManager,
    viewModel: LoginViewModel = viewModel()
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()

    // Observe login status (using DataStore) to navigate to Home or Sign In
    val context = LocalContext.current
    val isLoggedIn by dataStoreManager.isLoggedIn.collectAsState(initial = false)

    // If the user is logged in, navigate to Home
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }  // Pop the login screen from back stack
            }
        }
    }

    // Login UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Enter Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Enter Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        val coroutineScope = rememberCoroutineScope()


        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    coroutineScope.launch {
                        viewModel.loginUser()
                        Log.d("MYTAG", "LoginScreen: ${viewModel.getAllUser()}")
                        print("user list ${viewModel.getAllUser()}")
                        if (viewModel.isNewUser(username)) {
                            // Call suspend function to set login state in DataStore
                            dataStoreManager.setLoginState(true)
                            Toast.makeText(context, "Signed Up Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            if (viewModel.isValidUser(username, password)) {
                                dataStoreManager.setLoginState(true)
                                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate("home")
                            } else {
                                Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Please Enter Valid Username & Password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login / Sign Up")
        }
    }
}
