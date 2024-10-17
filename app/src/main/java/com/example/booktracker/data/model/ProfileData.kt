package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileData (
    val id: String,
    val username: String?,
    val avatar_url: String?
)