package com.example.movie_catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName(value = "poster_path")
    var image : String,
    var title : String
)