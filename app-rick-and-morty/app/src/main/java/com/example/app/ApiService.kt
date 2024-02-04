package com.example.app

import com.google.gson.Gson
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com/api/"

private val gsonInstance = Gson()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    @GET("character")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>
}

data class MovieResponse(
    val results: List<Character>,
    val page: Int,
    @SerialName("total_pages")
    val totalPages: Int
)

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}
