package com.androiddev.assignment.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_table")
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subTitle: String,
    val status: String,
    val thumb: String,
    val summary: String,
    val authors: String,
    val genres: String,
    val nsfw: Boolean,
    val type: String,
    val totalChapter: Int,
    val createAt: Long,
    val updateAt: Long
)
