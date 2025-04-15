package com.androiddev.assignment.data.api

import com.androiddev.assignment.data.model.MangaResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApi {

    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int,
        @Query("genres") genres: String = "Harem,Fantasy",
        @Query("nsfw") nsfw: Boolean = true,
        @Query("type") type: String = "all"
    ): MangaResponse

    companion object {
        const val BASE_URL = "https://mangaverse-api.p.rapidapi.com/"

        fun create(): MangaApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-RapidAPI-Key", "YOUR_API_KEY")
                        .addHeader("X-RapidAPI-Host", "mangaverse-api.p.rapidapi.com")
                        .build()
                    chain.proceed(request)
                }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MangaApi::class.java)
        }
    }
}
