package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    val followed_series_count: Int,
    val owned_volumes_count: Int,
    val read_volumes_count: Int,
    val most_read_volume_title: String?,
    val most_read_volume_cover_url: String?,
    val volumes_read_last_month: Int,
    val ownership_read_percentage: Float
)