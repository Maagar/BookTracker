package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSeries(
    val id: Int,
    val title: String,
    val main_cover_url: String,
    val is_single_volume: Boolean,
    val release_date: String,
    val synopsis: String,
    var total_volumes_released: Int
)

@Serializable
data class FollowedSeries(
    val id: Int,
    val volumes_read_count: Int,
    val series: UserSeries
)
