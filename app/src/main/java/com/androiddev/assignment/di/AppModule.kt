package com.androiddev.assignment.di

import android.app.Application
import androidx.room.Room
import com.androiddev.assignment.data.api.MangaApiService
import com.androiddev.assignment.data.db.MangaDao
import com.androiddev.assignment.data.db.MangaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module // <-- add this
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader(
                                "x-rapidapi-key",
                                "aa7077e744msh276595afced1bf9p1f0c7fjsn3a642fa8ba0a"
                            )
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMangaApiService(retrofit: Retrofit): MangaApiService {
        return retrofit.create(MangaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MangaDatabase {
        return Room.databaseBuilder(
            app,
            MangaDatabase::class.java,
            "manga_db"
        ).build()
    }

    @Provides
    fun provideMangaDao(db: MangaDatabase): MangaDao = db.mangaDao()
}
