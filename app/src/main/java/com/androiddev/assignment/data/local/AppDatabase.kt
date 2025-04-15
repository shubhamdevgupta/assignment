package com.androiddev.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androiddev.assignment.data.model.Manga
import com.androiddev.assignment.data.model.User

@Database(
    entities = [User::class, Manga::class],
    version = 1,
    exportSchema = false // ✅ add this
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mangaDao(): MangaDao
}