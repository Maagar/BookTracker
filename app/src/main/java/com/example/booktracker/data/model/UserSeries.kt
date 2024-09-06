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
    val totalVolumes: Int = 0,
    val readVolumes: Int = 0
)

@Serializable
data class FollowedSeries(
    val id: Int,
    val series: UserSeries
)