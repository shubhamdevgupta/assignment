package com.androiddev.assignment.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.androiddev.assignment.data.api.MangaApi
import com.androiddev.assignment.data.local.MangaDao
import com.androiddev.assignment.data.model.Manga

class MangaRepository(private val context: Context) {

    suspend fun fetchManga(page: Int = 1, genres: String = "Harem,Fantasy"): List<Manga> {
        // Replace this with actual Retrofit API logic
        // For now, let's simulate static data
        return listOf(
            Manga(id = "1", title = "One Piece", summary = "Pirate adventure story."),
            Manga(id = "2", title = "Naruto", summary = "Ninja journey of Naruto."),
            Manga(id = "3", title = "Attack on Titan", summary = "Battle between humans and titans.")
        )
    }
}

