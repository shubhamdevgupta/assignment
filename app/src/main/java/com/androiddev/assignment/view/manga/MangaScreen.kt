import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.androiddev.assignment.view.manga.MangaViewModel

@Composable
fun MangaScreen(viewModel: MangaViewModel = hiltViewModel(), navController: NavController) {
    val mangaList by viewModel.mangaList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.syncManga(1)
    }

    LazyColumn {
        items(mangaList) { manga ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("manga_detail/${manga.id}")
                    }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(manga.title)
                    Text(manga.summary.take(100))
                }
            }
        }
    }
}
