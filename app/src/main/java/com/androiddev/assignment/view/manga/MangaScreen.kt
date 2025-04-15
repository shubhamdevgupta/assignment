package com.androiddev.assignment.view.manga

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androiddev.assignment.data.datastore.DataStoreManager
import com.androiddev.assignment.data.model.Manga
import com.androiddev.assignment.view.face.FaceDetectionScreen

@Composable
fun MainScreen(
    navControllerRoot: NavController, // Root NavController from MainActivity
    dataStoreManager: DataStoreManager,
    mangaViewModel: MangaViewModel = hiltViewModel() // Assuming you use Hilt
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
                    icon = { Icon(Icons.Default.Person, contentDescription = "Face Detection") },
                    label = { Text("Face Detection") },
                    selected = currentDestination == "Face Detection",
                    onClick = { navController.navigate("Face Detection") }
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
                MangaScreen(navController)
            }
            composable("Face Detection") {
                FaceDetectionScreen()
            }
        }
    }
}

@Composable
fun MangaScreen(navController: NavController, mangaViewModel: MangaViewModel = hiltViewModel()) {
    val mangaList by mangaViewModel.mangaList.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Manga List", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(mangaList) { manga ->
                MangaItem(manga) {
                    // Navigate to the Manga Detail Screen
                    navController.navigate("MangaDetail/${manga.id}")
                }
            }
        }
    }
}

@Composable
fun MangaItem(manga: Manga, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = manga.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = manga.summary)
        }
    }
}

@Composable
fun MangaDetailScreen(mangaId: String, mangaViewModel: MangaViewModel = hiltViewModel()) {
    // Fetch manga details based on the ID passed to the screen
    val mangaDetails by mangaViewModel.getMangaDetails(mangaId).collectAsState(initial = null)

    mangaDetails?.let { manga ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(text = manga.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = manga.summary)
            // Add any additional manga details here
        }
    } ?: run {
        CircularProgressIndicator()
    }
}
