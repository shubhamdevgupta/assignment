package com.androiddev.assignment.view.manga

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.room.Room
import com.androiddev.assignment.data.api.MangaApi
import com.androiddev.assignment.data.local.AppDatabase
import com.androiddev.assignment.data.model.Manga
import com.androiddev.assignment.data.repository.MangaRepository
import com.androiddev.assignment.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MangaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MangaRepository(application.applicationContext)

    private val _mangaList = MutableStateFlow<List<Manga>>(emptyList())
    val mangaList: StateFlow<List<Manga>> = _mangaList.asStateFlow()

    init {
        viewModelScope.launch {
            val mangas = repository.fetchManga()
            _mangaList.value = mangas
        }
    }

    fun getMangaById(mangaId: String): Manga? {
        return _mangaList.value.find { it.id == mangaId }
    }
}

