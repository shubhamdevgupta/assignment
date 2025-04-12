package com.androiddev.assignment.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import com.androiddev.assignment.data.datastore.DataStoreManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, dataStoreManager: DataStoreManager) {
    val isLoggedIn by dataStoreManager.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(key1 = isLoggedIn) {
        delay(1000) // optional: small splash delay
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Simple Splash UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...", style = MaterialTheme.typography.headlineMedium)
    }
}
