package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val id: Int,
    val title: String,
    val cover_url: String,
    val volume_number: Int,
    val user_volume_id: Int? = null,
    var times_read: Int = 0,
    val owned: Boolean = false

)

@Serializable
data class VolumeToInsert(
    val volume_id: Int,
    val times_read: Int,
    val owned: Boolean
)

@Serializable
data class VolumeToUpdate(
    val id: Int,
    val volume_id: Int,
    val times_read: Int,
    val owned: Boolean
)