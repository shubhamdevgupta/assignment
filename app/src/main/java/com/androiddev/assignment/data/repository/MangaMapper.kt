package com.androiddev.assignment.data.repository


import com.androiddev.assignment.data.api.MangaDto
import com.androiddev.assignment.data.db.MangaEntity
import com.androiddev.assignment.data.model.Manga
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun MangaDto.toEntity(): MangaEntity {
    return MangaEntity(
        id = id,
        title = title,
        subTitle = sub_title,
        status = status,
        thumb = thumb,
        summary = summary,
        authors = Gson().toJson(authors),
        genres = Gson().toJson(genres),
        nsfw = nsfw,
        type = type,
        totalChapter = total_chapter,
        createAt = create_at,
        updateAt = update_at
    )
}

fun MangaEntity.toDomain(): Manga {
    return Manga(
        id = id,
        title = title,
        subTitle = subTitle,
        status = status,
        thumb = thumb,
        summary = summary,
        authors = Gson().fromJson(authors, object : TypeToken<List<String>>(){}.type),
        genres = Gson().fromJson(genres, object : TypeToken<List<String>>(){}.type),
        nsfw = nsfw,
        type = type,
        totalChapter = totalChapter,
        createAt = createAt,
        updateAt = updateAt
    )
}
