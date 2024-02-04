package com.example.app

import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<ApiResponse>
}

data class ApiResponse(
    val results: List<Character>,
    val page: Int,
    @SerialName("total_pages")
    val totalPages: Int
)

object CharacterApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
