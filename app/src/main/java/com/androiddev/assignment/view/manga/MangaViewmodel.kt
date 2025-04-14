package com.androiddev.assignment.view.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.assignment.data.model.Manga
import com.androiddev.assignment.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {

    val mangaList: StateFlow<List<Manga>> = repository.getMangaList()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun syncManga(page: Int = 1) {
        viewModelScope.launch {
            try {
                repository.syncManga(page)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
