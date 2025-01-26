package com.example.booktracker.data.network

import kotlinx.serialization.Serializable

@Serializable
data class getFollowedSeriesParams(
    val p_offset: Int,
    val p_limit: Int,
    val p_sort_by_date: Boolean,
    val p_show_finished: Boolean
)

@Serializable
class seriesParams (
    val p_offset: Int,
    val p_limit: Int,
    val p_search_query: String
)