package com.example.app

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val image : String?,
    val name : String,
    val status : String,
    val gender : String,
    val origin : Origin
)

@Serializable
data class Origin(
    @SerializedName("name")
    val name: String
)