package com.androiddev.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androiddev.assignment.data.datastore.DataStoreManager
import com.androiddev.assignment.view.SplashScreen
import com.androiddev.assignment.view.home.HomeScreen
import com.androiddev.assignment.view.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val dataStoreManager = DataStoreManager(this)

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(navController, dataStoreManager)
                }
                composable("login") {
                    LoginScreen(navController, dataStoreManager)
                }
                composable("home") {
                    HomeScreen(navController,dataStoreManager)
                }
            }
        }
    }
}
