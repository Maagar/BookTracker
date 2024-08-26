package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
class Series (
    val id: Int,
    val created_at: String,
    val title: String,
    val main_cover_url: String,
    val is_single_volume: Boolean,
    val release_date: String,
    val synopsis: String
)