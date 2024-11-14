package com.example.booktracker.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingVolume (
    val id: Int,
    val series_id: Int,
    val title: String,
    val series_title: String,
    val cover_url: String?,
    val release_date: LocalDate?,
    val volume_number: Int,
    ) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    fun daysRemaining(): Int? {
        return release_date?.let {
            today.daysUntil(it)
        }
    }
}