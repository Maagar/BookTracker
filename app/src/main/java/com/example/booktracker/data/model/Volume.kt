package com.example.booktracker.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val id: Int,
    val title: String,
    val cover_url: String? = null,
    val volume_number: Int,
    val user_volume_id: Int? = null,
    val release_date: LocalDate?,
    var times_read: Int = 0,
    val owned: Boolean = false,
    val synopsis: String?,
    val read_date: LocalDate? = null,
    val rating: Int? = null
)

@Serializable
data class VolumeToInsert(
    val volume_id: Int,
    val times_read: Int,
    val owned: Boolean
)

@Serializable
data class VolumeToUpdate(
    val id: Int, // user_volume_id
    val volume_id: Int,
    val times_read: Int,
    val owned: Boolean,
    val rating: Int?
)

@Serializable
data class VolumeResponse(val id: Int)