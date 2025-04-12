package com.androiddev.assignment.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.androiddev.assignment.data.datastore.DataStoreManager
import com.androiddev.assignment.view.face.FaceDetectionScreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navControllerRoot: NavController, // Root NavController from MainActivity
    dataStoreManager: DataStoreManager
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentDestination?.route
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Manga") },
                    label = { Text("Manga") },
                    selected = currentDestination == "Manga",
                    onClick = { navController.navigate("Manga") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Face Detectiion") },
                    label = { Text("Face Detectiion") },
                    selected = currentDestination == "Face Detectiion",
                    onClick = { navController.navigate("Face Detectiion") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "Manga",
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable("Manga") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Home Screen", modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                dataStoreManager.setLoginState(false) // Set loggedIn to false
                                navControllerRoot.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Logout")
                    }
                }
            }
            composable("Face Detectiion") {
             FaceDetectionScreen()
        }
        }
    }
}
