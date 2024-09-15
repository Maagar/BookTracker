package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val id: Int,
    val title: String,
    val cover_url: String,
    val volume_number: Int,
    private val user_volumes: List<UserVolumes> = emptyList()
) {
    val userVolumes: UserVolumes
        get() = UserVolumes(
            user_volumes.firstOrNull()?.id,
            user_volumes.sumOf { it.times_read },
            user_volumes.any { it.owned })
}

@Serializable
data class UserVolumes(
    val id: Int? = null,
    val times_read: Int = 0,
    val owned: Boolean = false
)

@Serializable
data class VolumeToUpsert(
    val id: Int?,
    val volume_id: Int,
    val times_read: Int,
    val owned: Boolean
)