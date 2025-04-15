package com.androiddev.assignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class Manga(
    @PrimaryKey val id: String,
    val title: String,
    val status: String,
    val thumb: String,
    val summary: String,
    val nsfw: Boolean,
    val type: String,
    val update_at: Long
)
