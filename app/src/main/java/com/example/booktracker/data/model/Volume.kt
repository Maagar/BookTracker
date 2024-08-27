package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val id: Int,
    val title: String,
    val cover_url: String,
    val volume_number: Int,
    val user_volumes: List<UserVolumes> = emptyList()
) {
    val timesRead: Int get() = user_volumes.sumOf { it.times_read ?: 0 }
}

@Serializable
data class UserVolumes(
    val times_read: Int? = 0
)