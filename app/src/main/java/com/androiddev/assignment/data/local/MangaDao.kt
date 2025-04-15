package com.androiddev.assignment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androiddev.assignment.data.model.Manga

@Dao
interface MangaDao {

    @Query("SELECT * FROM manga ORDER BY update_at DESC")
    fun getAllManga(): PagingSource<Int, Manga>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangaList: List<Manga>)

    @Query("DELETE FROM manga")
    suspend fun clearAll()
}
