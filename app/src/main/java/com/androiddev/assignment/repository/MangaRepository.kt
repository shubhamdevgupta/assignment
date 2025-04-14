package com.androiddev.assignment.repository

import com.androiddev.assignment.data.api.MangaApiService
import com.androiddev.assignment.data.local.db.MangaDao
import com.androiddev.assignment.model.Manga
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val apiService: MangaApiService,
    private val mangaDao: MangaDao
) {
    fun getMangaList(): Flow<List<Manga>> {
        return mangaDao.getAllManga().map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun syncManga(page: Int) {
        val response = apiService.fetchMangaList(page)
        val mangaEntities = response.data.map { it.toEntity() }
        mangaDao.insertAll(mangaEntities)
    }
}
