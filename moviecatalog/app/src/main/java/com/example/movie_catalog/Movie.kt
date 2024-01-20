package com.example.movie_catalog

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerializedName(value = "poster_path")
    val image : String?,
    val title : String,
    @SerializedName(value = "release_date")
    val releaseDate : String,
    val overview : String
)
