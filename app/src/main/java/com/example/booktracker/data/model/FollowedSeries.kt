package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FollowedSeries(
    val id: Int,
    val volumes_read_count: Int,
    val series: Series
)
