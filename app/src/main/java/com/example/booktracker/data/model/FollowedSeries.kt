package com.example.booktracker.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class FollowedSeries(
    val id: Int,
    val volumes_read_count: Int,
    private val series_id: Int,
    private val created_at: String,
    private val title: String,
    private val main_cover_url: String,
    private val is_single_volume: Boolean,
    private val release_date: LocalDate,
    private val synopsis: String,
    private var total_volumes_released: Int,
    private var isFollowing: Boolean = false
) {
    val series = Series(
        series_id,
        created_at,
        title,
        main_cover_url,
        is_single_volume,
        release_date,
        synopsis,
        total_volumes_released,
        isFollowing
    )
}
