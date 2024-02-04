package com.example.app

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val image : String?,
    val name : String,
)
