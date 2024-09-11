package com.example.booktracker.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Series (
    val id: Int,
    val created_at: String,
    val title: String,
    val main_cover_url: String,
    val is_single_volume: Boolean,
    val release_date: LocalDate,
    val synopsis: String,
    var total_volumes_released: Int,
    var isFollowing: Boolean = false
)

@Serializable
class UserSeriesIds(
    val series_id: Int
)