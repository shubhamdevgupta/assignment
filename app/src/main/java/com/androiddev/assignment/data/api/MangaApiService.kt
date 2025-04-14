package com.androiddev.assignment.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApiService {
    @GET("manga/fetch")
    suspend fun fetchMangaList(
        @Query("page") page: Int,
        @Query("genres") genres: String = "Harem,Fantasy",
        @Query("nsfw") nsfw: Boolean = true,
        @Query("type") type: String = "all"
    ): MangaApiResponse
}
