package com.androiddev.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androiddev.assignment.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}